package com.example.bill.services;

import java.math.BigDecimal;

public class ElectricityBill extends Bill {

    private BigDecimal costPerKhw;
    private Double totalKhwConsumed;

    public BigDecimal getCostPerKhw() {
        return costPerKhw;
    }

    public void setCostPerKhw(BigDecimal costPerKhw) {
        this.costPerKhw = costPerKhw;
    }

    public Double getTotalKhwConsumed() {
        return totalKhwConsumed;
    }

    public void setTotalKhwConsumed(Double totalKhwConsumed) {
        this.totalKhwConsumed = totalKhwConsumed;
    }

}
