package com.example.calculator.service;

import com.example.calculator.model.CalculationRequest;
import com.example.calculator.repository.CalculatorTransactionRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CalculatorServiceTest {

    private final AtomicReference<com.example.calculator.model.CalculationResult> persisted = new AtomicReference<>();
    private final CalculatorTransactionRepository repository = calculation -> {
        persisted.set(calculation);
        return calculation.transactionUuid();
    };
    private final CalculatorService service = new CalculatorService(repository);

    @Test
    void addsAndPersistsTheTransaction() {
        var result = service.add(new CalculationRequest(new BigDecimal("25.5"), new BigDecimal("10.0")));

        assertThat(result.operation()).isEqualTo("ADD");
        assertThat(result.result()).isEqualByComparingTo("35.5");
        assertThat(persisted.get()).isEqualTo(result);
    }

    @Test
    void multipliesAndPersistsTheTransaction() {
        var result = service.multiply(new CalculationRequest(new BigDecimal("6"), new BigDecimal("7")));

        assertThat(result.operation()).isEqualTo("MULTIPLY");
        assertThat(result.result()).isEqualByComparingTo("42");
        assertThat(persisted.get()).isEqualTo(result);
    }

    @Test
    void rejectsAnOperandOutsideTheAllowedRange() {
        assertThatThrownBy(() -> service.add(
                new CalculationRequest(new BigDecimal("1000.01"), BigDecimal.ZERO)
        ))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("value1 must be in range [-1000, 1000]");
    }
}
