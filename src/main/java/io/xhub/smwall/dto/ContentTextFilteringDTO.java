package io.xhub.smwall.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ContentTextFilteringDTO {
    private Comment comment;
    private List<String> textLanguages;

    private Map<String,Object> requestedAttributes;

    @Getter
    @Setter
    public static class Comment {
        private String text;
    }
}
