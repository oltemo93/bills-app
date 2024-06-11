package com.example.bill.services.electricity;

import com.example.bill.services.Bill;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ElectricityBill extends Bill {

    @Schema(defaultValue = "electricity")
    private String type = "electricity";
    @NotNull
    private BigDecimal costPerKhw;
    @NotNull
    private Double totalKhwConsumed;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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
