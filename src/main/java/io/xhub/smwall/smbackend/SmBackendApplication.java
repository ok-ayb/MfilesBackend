package io.xhub.smwall.smbackend;

import io.xhub.smwall.smbackend.config.MetaProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties({MetaProperties.class})
@EnableFeignClients
@EnableScheduling
public class SmBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmBackendApplication.class, args);
    }

}
