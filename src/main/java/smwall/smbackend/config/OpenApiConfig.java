package smwall.smbackend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Social media wall REST API",
        version = "1.0",
        description = "Social media wall APIs Docs"
))
public class OpenApiConfig {
}
