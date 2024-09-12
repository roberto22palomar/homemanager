package com.example.homemanager.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "HomeManager API", version = "1.0", description = "Documentación para endpoints en HomeManager")
)
public class OpenApiConfig {


}
