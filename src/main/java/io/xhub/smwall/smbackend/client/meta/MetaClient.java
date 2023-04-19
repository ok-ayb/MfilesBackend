package io.xhub.smwall.smbackend.client.meta;

import io.xhub.smwall.smbackend.client.meta.response.IGHashtaggedMediaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "meta", url = "https://graph.facebook.com/v16.0")
public interface MetaClient {
    @GetMapping(value = "/{hashtagId}/recent_media?fields={fields}&user_id={userId}")
    IGHashtaggedMediaResponse getRecentHashtaggedMedia(@PathVariable String hashtagId,
                                                       @PathVariable String fields,
                                                       @PathVariable String userId);
}
