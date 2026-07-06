package com.armaan.apiportal.config; // Ensure this matches your package structure

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        String securitySchemeName = "BearerAuth";

        // Keep your forced HTTPS server rule active for Railway proxying
        Server productionServer = new Server();
        productionServer.setUrl("https://api-portal-production-123.up.railway.app");
        productionServer.setDescription("Production Server");

        return new OpenAPI()
                .servers(List.of(productionServer))
                // 1. Declare the bearer token layout inside OpenAPI Components
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                // 2. Map the requirement globally so every endpoint gets a lock icon
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName));
    }
}
