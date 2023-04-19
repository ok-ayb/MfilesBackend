package io.xhub.smwall.smbackend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "application.webhooks.meta", ignoreUnknownFields = true)
public class MetaProperties {
    private String verifyToken;
    private String appSecret;
    private String userId;
    private Map<String, String> hashtags;
}
