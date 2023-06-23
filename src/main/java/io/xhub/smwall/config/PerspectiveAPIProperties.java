package io.xhub.smwall.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application.text-filter.perspective-api")
public class PerspectiveAPIProperties {
    private String apiKey;

}
