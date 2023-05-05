package io.xhub.smwall.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "application.webhooks.youtube")
public class YoutubeProperties {
    private String apiKey;
    private String channelId;
}
