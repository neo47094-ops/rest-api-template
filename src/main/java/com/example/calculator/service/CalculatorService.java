package com.example.calculator.service;

import com.example.calculator.model.CalculationRequest;
import com.example.calculator.model.CalculationResult;
import com.example.calculator.model.Operation;
import com.example.calculator.repository.CalculatorTransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class CalculatorService {

    private static final BigDecimal MIN_VALUE = new BigDecimal("-1000");
    private static final BigDecimal MAX_VALUE = new BigDecimal("1000");

    private final CalculatorTransactionRepository repository;

    public CalculatorService(CalculatorTransactionRepository repository) {
        this.repository = repository;
    }

    public CalculationResult add(CalculationRequest request) {
        return calculate(request, Operation.ADD);
    }

    public CalculationResult multiply(CalculationRequest request) {
        return calculate(request, Operation.MULTIPLY);
    }

    private CalculationResult calculate(CalculationRequest request, Operation operation) {
        validate(request);
        BigDecimal result = operation == Operation.ADD
                ? request.value1().add(request.value2())
                : request.value1().multiply(request.value2());

        var calculation = new CalculationResult(
                UUID.randomUUID(),
                request.value1(),
                request.value2(),
                operation.name(),
                result
        );
        repository.create(calculation);
        return calculation;
    }

    private void validate(CalculationRequest request) {
        if (request == null || request.value1() == null) {
            throw new InvalidRequestException("INVALID_REQUEST_BODY", "invalid request body");
        }
        validateOperand("value1", request.value1());

        if (request.value2() == null) {
            throw new InvalidRequestException("INVALID_REQUEST_BODY", "invalid request body");
        }
        validateOperand("value2", request.value2());
    }

    private void validateOperand(String name, BigDecimal value) {
        if (value.compareTo(MIN_VALUE) < 0 || value.compareTo(MAX_VALUE) > 0) {
            throw new InvalidRequestException(
                    name.toUpperCase() + "_OUT_OF_RANGE",
                    name + " must be in range [-1000, 1000]"
            );
        }
    }
}
