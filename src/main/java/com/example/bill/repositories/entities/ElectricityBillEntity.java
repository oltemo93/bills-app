package com.example.bill.repositories.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="electricity_bill")
public class ElectricityBillEntity {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    private LocalDate periodStart;
    private LocalDate periodEnd;
    private BigDecimal costPerKhw;
    private Double totalKhwConsumed;
    private BigDecimal totalAmount;
    @Column(name="balance")
    private BigDecimal balance;

    /**
     * period_start DATE,
     *   period_end DATE,
     *   cost_per_khw NUMERIC,
     *   total_khw_consumed INTEGER,
     *   total_amount NUMERIC,
     *   balance   NUMERIC
     * @return
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDate getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(LocalDate periodStart) {
        this.periodStart = periodStart;
    }

    public LocalDate getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(LocalDate periodEnd) {
        this.periodEnd = periodEnd;
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

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
