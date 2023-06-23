package io.xhub.smwall.client;

import io.xhub.smwall.client.request.CommentAnalyzerRequest;
import io.xhub.smwall.filter.responses.TextFilteringResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "perspective", url = "https://commentanalyzer.googleapis.com/v1alpha1")
public interface PerspectiveAPIClient {
    @PostMapping(value = "/comments:analyze")
    TextFilteringResponse analyzeText(
            @RequestParam(value = "key") String apiKey,
            @RequestBody CommentAnalyzerRequest request
    );

}
