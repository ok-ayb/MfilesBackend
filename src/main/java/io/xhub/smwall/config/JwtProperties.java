package io.xhub.smwall.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "application.security.auth.jwt")
public class JwtProperties {
    private String base64Secret;
    private long tokenValidity;
}
