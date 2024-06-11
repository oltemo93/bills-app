package com.example.bill.services.electricity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class OutstandingDebtCalculatorTest {

    @ParameterizedTest
    @ArgumentsSource(CalculatorArgumentsProvider.class)
    @DisplayName("Parameterized test for various kWh consumed and cost per kWh")
    void testCalculateParameterized(double consumedKhw, BigDecimal costPerKhw, BigDecimal expectedAmount) {
        // Act
        BigDecimal actualAmount = OutstandingDebtCalculator.calculate(consumedKhw, costPerKhw);

        // Assert
        assertThat(actualAmount, is(expectedAmount));
    }

    static class CalculatorArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of(100.0, BigDecimal.valueOf(1.5), BigDecimal.valueOf(150.0).setScale(2)),
                    Arguments.of(200.0, BigDecimal.valueOf(2.0), BigDecimal.valueOf(400.0).setScale(2)),
                    Arguments.of(0.0, BigDecimal.valueOf(1.5), BigDecimal.ZERO.setScale(2)),
                    Arguments.of(100.0, BigDecimal.ZERO, BigDecimal.ZERO.setScale(1)),
                    Arguments.of(-100.0, BigDecimal.valueOf(1.5), BigDecimal.valueOf(-150.0).setScale(2))
            );
        }
    }

}