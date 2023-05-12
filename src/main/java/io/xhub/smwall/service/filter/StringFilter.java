package io.xhub.smwall.service.filter;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StringFilter extends Filter<String> {
    private String contains;
    private String startsWith;
    private String endsWith;
    private List<String> between;
    private String goe;
    private String gt;
    private String loe;
    private String lt;
}
