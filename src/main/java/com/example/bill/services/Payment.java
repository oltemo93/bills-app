package com.example.bill.services;

import java.math.BigDecimal;

public class Payment {

    private BigDecimal amountToPay;

    public BigDecimal getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(BigDecimal amountToPay) {
        this.amountToPay = amountToPay;
    }

}
