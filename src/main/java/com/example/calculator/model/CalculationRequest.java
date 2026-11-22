package com.example.calculator.model;

import java.math.BigDecimal;

public record CalculationRequest(BigDecimal value1, BigDecimal value2) {
}
