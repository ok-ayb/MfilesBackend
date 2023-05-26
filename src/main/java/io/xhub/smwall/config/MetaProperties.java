package io.xhub.smwall.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "application.webhooks.meta")
public class MetaProperties {
    private String verifyToken;
    private String appSecret;
    private String userId;
    private String resourceIdPrefix;
    private Map<String, String> hashtags;
    private String username;
}
