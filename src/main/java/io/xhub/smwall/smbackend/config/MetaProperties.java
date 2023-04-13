package io.xhub.smwall.smbackend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "application.webhooks.meta", ignoreUnknownFields = false)
public class MetaProperties {
    private String verifyToken;
    private String appSecret;
}
