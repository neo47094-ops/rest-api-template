package com.example.calculator.repository;

import com.example.calculator.model.CalculationResult;
import java.util.UUID;

public interface CalculatorTransactionRepository {

    UUID create(CalculationResult calculation);
}
