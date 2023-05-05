package io.xhub.smwall;

import io.xhub.smwall.config.EhcacheProperties;
import io.xhub.smwall.config.MetaProperties;
import io.xhub.smwall.config.YoutubeProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties({MetaProperties.class, YoutubeProperties.class, EhcacheProperties.class})
@EnableFeignClients
@EnableScheduling
public class SocialMediaWallApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialMediaWallApplication.class, args);
    }

}
