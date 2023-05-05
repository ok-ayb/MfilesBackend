package io.xhub.smwall.client;

import io.xhub.smwall.client.response.YoutubeMediaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "youtube", url = "https://www.googleapis.com/youtube/v3")
public interface YoutubeClient {

    @GetMapping(value = "/search")
    YoutubeMediaResponse getRecentChannelShorts(
            @RequestParam @Validated Map<String, String> searchParams,
            @RequestParam(value = "channelId") String channelId,
            @RequestParam(value = "key") String apiKey);
}
