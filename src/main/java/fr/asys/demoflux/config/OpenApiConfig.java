package fr.asys.demoflux.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Music flux Core API", description = "API descriptions for music flux", version = "1.0"))
public class OpenApiConfig {
}
