package life.superapp.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CalculationRequest(
        @JsonProperty("mandatory_pension_contributions") int mandatoryPensionContributions,
        @JsonProperty("voluntary_pension_contributions") int voluntaryPensionContributions,
        @JsonProperty("own_expenses") int ownExpenses,
        @JsonProperty("guaranteed_period_years") int guaranteedPeriodYears,
        @JsonProperty("mandatory_professional_pension_contributions") MandatoryProfessionalPensionContributions oppv,
        @JsonProperty("disability") String disability,
        @JsonProperty("other_life_insurance_company_redemption") int otherInsuranceRedemption
) {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record MandatoryProfessionalPensionContributions (
            @JsonProperty("amount") int amount,
            @JsonProperty("months") int months
    ) {}
}
