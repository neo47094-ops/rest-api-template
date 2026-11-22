package com.example.calculator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AppProperties(
        String apiKey,
        boolean openapiUiEnabled,
        String openapiUiPath
) {
}
