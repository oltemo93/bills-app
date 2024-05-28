package com.example.bill.services;

import java.math.BigDecimal;

public class Payment {

    private Bill bill;
    private BigDecimal amountToPay;

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public BigDecimal getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(BigDecimal amountToPay) {
        this.amountToPay = amountToPay;
    }
}
