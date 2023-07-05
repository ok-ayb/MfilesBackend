package io.xhub.smwall.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application.media-filter")
public class ContentFilterProperties {
    private String urlScriptImage;
    private String urlScriptVideo;

}
