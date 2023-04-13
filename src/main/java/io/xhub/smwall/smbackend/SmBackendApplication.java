package io.xhub.smwall.smbackend;

import io.xhub.smwall.smbackend.config.MetaProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({MetaProperties.class})
public class SmBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmBackendApplication.class, args);
    }

}
