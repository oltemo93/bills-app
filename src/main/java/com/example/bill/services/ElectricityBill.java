package com.example.bill.services;

import java.math.BigDecimal;

public class ElectricityBill extends Bill {

    private BigDecimal costPerKhw;
    private Double totalKwhConsumed;

    public BigDecimal getCostPerKhw() {
        return costPerKhw;
    }

    public void setCostPerKhw(BigDecimal costPerKhw) {
        this.costPerKhw = costPerKhw;
    }

    public Double getTotalKwhConsumed() {
        return totalKwhConsumed;
    }

    public void setTotalKwhConsumed(Double totalKwhConsumed) {
        this.totalKwhConsumed = totalKwhConsumed;
    }

}
