package io.xhub.smwall.filter.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TextFilteringResponse {
    @JsonProperty("attributeScores")
    private RequestedAttributesResponse attributeScores;
    private List<String> languages;
    private List<String> detectedLanguages;
}
