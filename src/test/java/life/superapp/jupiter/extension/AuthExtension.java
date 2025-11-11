package life.superapp.jupiter.extension;

import life.superapp.api.service.AuthClient;
import life.superapp.api.service.impl.AuthApiClient;
import life.superapp.jupiter.annotation.AccessToken;
import life.superapp.jupiter.annotation.Auth;
import life.superapp.jupiter.annotation.OneTimeToken;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;
import org.mozilla.javascript.Token;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;

import static life.utils.config.TestConfig.testConfig;


public class AuthExtension implements
        BeforeEachCallback,
        ParameterResolver {
    private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(AuthExtension.class);
    private static final Duration TTL = Duration.ofMinutes(50);

    private final AuthClient authClient = new AuthApiClient();

    private record Tokens(String access, Instant createdAt) {}


    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        AnnotationSupport.findAnnotation(extensionContext.getRequiredTestMethod(), Auth.class)
                .ifPresent(ann -> {
                    Parameter[] params = extensionContext.getRequiredTestMethod().getParameters();
                    boolean needOtt = Arrays.stream(params)
                            .anyMatch(p -> p.isAnnotationPresent(OneTimeToken.class) || p.isAnnotationPresent(AccessToken.class));
                    boolean needAccess = Arrays.stream(params)
                            .anyMatch(p -> p.isAnnotationPresent(AccessToken.class));

                    if (!needOtt) return;

                    String iin = ann.iin();
                    String fullName = ann.fullName();


                    if (!needAccess) {
                        String freshOtt = getOttSafely(iin, fullName);
                        extensionContext.getStore(NAMESPACE).put(methodOttKey(extensionContext), freshOtt); // method-scope
                        return;
                    }


                    ExtensionContext root = extensionContext.getRoot();
                    String accessKey = accessKey(iin, fullName);
                    Tokens cached = root.getStore(NAMESPACE).get(accessKey, Tokens.class);
                    if (cached == null || isExpired(cached)) {
                        String freshOtt = getOttSafely(iin, fullName);
                        String access = getAccessSafely(freshOtt);
                        root.getStore(NAMESPACE).put(accessKey, new Tokens(access, Instant.now()));
                    }
                });

    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return (parameterContext.isAnnotated(OneTimeToken.class))
                || (parameterContext.isAnnotated(AccessToken.class));
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {

        Auth authAnn = AnnotationSupport.findAnnotation(extensionContext.getRequiredTestMethod(), Auth.class)
                .orElseThrow(() -> new ParameterResolutionException("@Auth is missing on test method/class"));

        if (parameterContext.isAnnotated(OneTimeToken.class)) {
            String ott = extensionContext.getStore(NAMESPACE).get(methodOttKey(extensionContext), String.class);
            if (ott == null || ott.isBlank()) {
                ott = getOttSafely(authAnn.iin(), authAnn.fullName());
                extensionContext.getStore(NAMESPACE).put(methodOttKey(extensionContext), ott);
            }
            return ott;
        }

        if (parameterContext.isAnnotated(AccessToken.class)) {
            Tokens entry = extensionContext.getRoot().getStore(NAMESPACE).get(accessKey(authAnn.iin(), authAnn.fullName()), Tokens.class);
            if (entry == null || isExpired(entry)) {
                String freshOtt = getOttSafely(authAnn.iin(), authAnn.fullName());
                String access = getAccessSafely(freshOtt);
                entry = new Tokens(access, Instant.now());
                extensionContext.getRoot().getStore(NAMESPACE).put(accessKey(authAnn.iin(), authAnn.fullName()), entry);
            }
            return entry.access();
        }

        throw new ParameterResolutionException("Unsupported parameter");

    }


    private boolean isExpired(Tokens e) {
        return e == null || Instant.now().isAfter(e.createdAt().plus(TTL));
    }

    private String methodOttKey(ExtensionContext ctx) {
        return ctx.getUniqueId() + ":ott";
    }

    private String envBaseUrl() {
        return testConfig().apiSuperAppUrl();
    }

    private String accessKey(String iin, String fullName) {
        return envBaseUrl() + "|" + iin + "|" + fullName + "|access";
    }

    private String getOttSafely(String iin, String fullName) {
        try {
            return authClient.getOtt(iin, fullName);
        } catch (Exception e) {
            throw new ExtensionConfigurationException("Failed to obtain one-time token", e);
        }
    }

    private String getAccessSafely(String ott) {
        try {
            return authClient.getAccessToken(ott);
        } catch (Exception e) {
            throw new ExtensionConfigurationException("Failed to obtain access token", e);
        }
    }
}
