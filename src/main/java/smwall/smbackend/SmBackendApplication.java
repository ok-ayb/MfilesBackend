package smwall.smbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import smwall.smbackend.config.MetaProperties;

@SpringBootApplication
@EnableConfigurationProperties({MetaProperties.class})
public class SmBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmBackendApplication.class, args);
    }

}
