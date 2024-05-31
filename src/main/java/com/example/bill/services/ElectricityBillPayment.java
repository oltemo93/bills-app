package com.example.bill.services;

import java.math.BigDecimal;

public class ElectricityBillPayment {

    private ElectricityBill bill;
    private BigDecimal amountToPay;

    public ElectricityBill getBill() {
        return bill;
    }

    public void setBill(ElectricityBill bill) {
        this.bill = bill;
    }

    public BigDecimal getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(BigDecimal amountToPay) {
        this.amountToPay = amountToPay;
    }

}
