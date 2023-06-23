package io.xhub.smwall.client.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CommentAnalyzerRequest {
    private Comment comment;
    private List<String> languages;
    private Map<String, Map<String, Object>> requestedAttributes;

    @Getter
    @Setter
    public static class Comment {
        private String text;
    }
}
