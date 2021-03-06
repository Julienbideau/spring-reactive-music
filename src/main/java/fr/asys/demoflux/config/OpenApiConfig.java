package fr.asys.demoflux.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Music flux Core API", description = "API descriptions for music flux", version = "1.0"))
public class OpenApiConfig {
}
