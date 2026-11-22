package com.example.calculator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthcheckController {

    @GetMapping("/healthcheck")
    Map<String, String> healthcheck() {
        return Map.of("status", "ok");
    }
}
