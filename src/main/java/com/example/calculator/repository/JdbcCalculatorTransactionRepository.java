package com.example.calculator.repository;

import com.example.calculator.model.CalculationResult;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class JdbcCalculatorTransactionRepository implements CalculatorTransactionRepository {

    private final JdbcClient jdbcClient;

    public JdbcCalculatorTransactionRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public UUID create(CalculationResult calculation) {
        jdbcClient.sql("""
                INSERT INTO calculator_transactions (
                    transaction_uuid, value1, value2, operation, result,
                    created_by, updated_by
                ) VALUES (
                    :transactionUuid, :value1, :value2, :operation, :result,
                    'admin', 'admin'
                )
                """)
                .param("transactionUuid", calculation.transactionUuid())
                .param("value1", calculation.value1())
                .param("value2", calculation.value2())
                .param("operation", calculation.operation())
                .param("result", calculation.result())
                .update();

        return calculation.transactionUuid();
    }
}
