package com.example.bill.services;

import com.example.bill.services.electricity.ElectricityBill;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ElectricityBillPayment {

    @Schema(accessMode= Schema.AccessMode.READ_ONLY)
    private ElectricityBill bill;
    @NotNull
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
