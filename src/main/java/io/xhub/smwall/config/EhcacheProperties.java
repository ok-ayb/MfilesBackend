package io.xhub.smwall.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "application.cache.ehcache")
public class EhcacheProperties {
    private long timeToLive;
    private long maxEntries;
}
