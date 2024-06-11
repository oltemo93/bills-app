package com.example.bill.services.electricity;

import java.math.BigDecimal;

public class OutstandingDebtCalculator {

    public static BigDecimal calculate(double consumedKhw, BigDecimal costPerKhw) {
        BigDecimal totalKhwConsumed = BigDecimal.valueOf(consumedKhw);
        BigDecimal amount = costPerKhw.multiply(totalKhwConsumed);
        return amount;
    }

}
