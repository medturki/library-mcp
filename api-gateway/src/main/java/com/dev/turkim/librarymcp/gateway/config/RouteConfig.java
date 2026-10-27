package com.dev.turkim.librarymcp.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Public routes. {@code lb://NAME} is resolved through the service registry and load-balanced.
 */
@Configuration
public class RouteConfig {

    @Bean
    RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                // /api/library/mcp -> library-service /mcp (MCP endpoint)
                .route("library-service", r -> r.path("/api/library/**")
                        .filters(f -> f.rewritePath("/api/library/(?<rest>.*)", "/${rest}"))
                        .uri("lb://LIBRARY-SERVICE"))
                // /api/assistant/chat -> library-assistant /chat
                .route("library-assistant", r -> r.path("/api/assistant/**")
                        .filters(f -> f.rewritePath("/api/assistant/(?<rest>.*)", "/${rest}"))
                        .uri("lb://LIBRARY-ASSISTANT"))
                .build();
    }
}
