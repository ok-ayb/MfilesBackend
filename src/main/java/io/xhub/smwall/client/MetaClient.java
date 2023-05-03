package io.xhub.smwall.client;

import io.xhub.smwall.client.response.InstagramMediaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "meta", url = "https://graph.facebook.com/v16.0")
public interface MetaClient {
    @GetMapping("/{hashtagId}/recent_media")
    InstagramMediaResponse getIGHashtagRecentMedia(@PathVariable String hashtagId,
                                                   @RequestParam("user_id") String userId,
                                                   @RequestParam String fields);

    @GetMapping("/{userId}/stories")
    InstagramMediaResponse getIGUserStories(@PathVariable String userId,
                                            @RequestParam String fields);

    @GetMapping("/{userId}/tags")
    InstagramMediaResponse getIGUserTags(@PathVariable String userId,
                                         @RequestParam String fields);

    @GetMapping("/{userId}/media")
    InstagramMediaResponse getIGUserMedia(@PathVariable String userId,
                                          @RequestParam String fields);
}
