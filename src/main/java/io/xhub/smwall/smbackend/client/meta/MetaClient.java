package io.xhub.smwall.smbackend.client.meta;

import io.xhub.smwall.smbackend.client.meta.response.InstagramMediaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "meta", url = "https://graph.facebook.com/v16.0")
public interface MetaClient {
    @GetMapping(value = "/{hashtagId}/recent_media?fields={fields}&user_id={userId}")
    InstagramMediaResponse getRecentHashtaggedMedia(@PathVariable String hashtagId,
                                                    @PathVariable String fields,
                                                    @PathVariable String userId);

    @GetMapping(value = "/{userId}/tags?fields={fields}")
    InstagramMediaResponse getTaggedMedia(@PathVariable String fields,
                                          @PathVariable String userId);

    @GetMapping(value = "/{userId}/media?fields={fields}")
    InstagramMediaResponse getRecentUserMedia(@PathVariable String userId,
                                              @PathVariable String fields);
}
