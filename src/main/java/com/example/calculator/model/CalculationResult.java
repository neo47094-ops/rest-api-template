package com.example.calculator.model;

import java.math.BigDecimal;
import java.util.UUID;

public record CalculationResult(
        UUID transactionUuid,
        BigDecimal value1,
        BigDecimal value2,
        String operation,
        BigDecimal result
) {
}
