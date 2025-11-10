package life.superapp.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OttGatewayResponse(
        @JsonProperty("status") Boolean status,
        @JsonProperty("data") String oneTimeToken
) {
}
