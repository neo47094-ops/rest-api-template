package com.example.calculator.controller;

import com.example.calculator.model.CalculationRequest;
import com.example.calculator.model.CalculationResult;
import com.example.calculator.service.CalculatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/calculator/v1")
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping("/im-a-live")
    public String getCalculationResult(@RequestParam Map<String, String> requestParams) {
        return "Hello,World";
    }

    @PostMapping("/add")
    ResponseEntity<Map<String, Object>> add(@RequestBody CalculationRequest request) {
        return ok(calculatorService.add(request));
    }

    @PostMapping("/multiply")
    ResponseEntity<Map<String, Object>> multiply(@RequestBody CalculationRequest request) {
        return ok(calculatorService.multiply(request));
    }

    private ResponseEntity<Map<String, Object>> ok(CalculationResult result) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("transaction-uuid", result.transactionUuid());
        data.put("value1", result.value1());
        data.put("value2", result.value2());
        data.put("operation", result.operation());
        data.put("result", result.result());
        return ResponseEntity.ok(Map.of("data", data));
    }
}
