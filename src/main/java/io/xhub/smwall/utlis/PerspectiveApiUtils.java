package io.xhub.smwall.utlis;

import io.xhub.smwall.client.PerspectiveAPIClient;
import io.xhub.smwall.client.request.CommentAnalyzerRequest;
import io.xhub.smwall.config.PerspectiveAPIProperties;
import io.xhub.smwall.filter.responses.TextFilteringResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Component
@Slf4j
public class PerspectiveApiUtils {
    private final PerspectiveAPIProperties perspectiveAPIProperties;
    private final PerspectiveAPIClient perspectiveAPIClient;

    public TextFilteringResponse analyzeComment(String text) {
        try {
            CommentAnalyzerRequest.Comment comment = new CommentAnalyzerRequest.Comment();
            comment.setText(text);

            CommentAnalyzerRequest commentAnalyzerRequest = new CommentAnalyzerRequest();
            commentAnalyzerRequest.setComment(comment);
            commentAnalyzerRequest.setLanguages(List.of("en"));

            Map<String, Map<String, Object>> requestedAttributes = new HashMap<>();
            requestedAttributes.put("TOXICITY", new HashMap<>());
            commentAnalyzerRequest.setRequestedAttributes(requestedAttributes);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            TextFilteringResponse response = perspectiveAPIClient.analyzeText(perspectiveAPIProperties.getApiKey(), commentAnalyzerRequest);

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
