package io.xhub.smwall.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class WallFooterDTO {
    private String coOrganizer;
    private List<String> institutionalPartners;
    private Map<String, List<String>> sponsors;
    private String logoUrl;
}
