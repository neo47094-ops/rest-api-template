package com.example.calculator.controller;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@ConditionalOnProperty(name = "app.openapi-ui-enabled", havingValue = "true")
public class OpenApiController {

    @GetMapping(value = "/openapi.json", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> spec() throws IOException {
        var resource = new ClassPathResource("openapi/openapi.json");
        return ResponseEntity.ok(resource.getContentAsString(StandardCharsets.UTF_8));
    }

    @GetMapping(value = "${app.openapi-ui-path:/redoc}", produces = MediaType.TEXT_HTML_VALUE)
    String redoc() {
        return """
                <!doctype html>
                <html lang="en">
                  <head><meta charset="utf-8"><title>Calculator API</title></head>
                  <body>
                    <redoc spec-url="/openapi.json"></redoc>
                    <script src="https://cdn.redoc.ly/redoc/latest/bundles/redoc.standalone.js"></script>
                  </body>
                </html>
                """;
    }
}
