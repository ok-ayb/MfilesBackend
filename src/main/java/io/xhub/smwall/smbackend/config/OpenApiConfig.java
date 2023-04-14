package io.xhub.smwall.smbackend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Social media wall REST API",
        version = "1.0",
        description = "Social media wall APIs Docs"
),
        servers = @Server(
                url = " https://smwall.dev.x-hub.io/",
                description = "Dev server"
        ))
public class OpenApiConfig {
}
