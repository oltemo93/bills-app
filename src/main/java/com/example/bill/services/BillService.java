package com.example.bill.services;

import java.util.List;
import java.util.Optional;

public interface BillService {

    public long create(Bill bill);
    public void pay(ElectricityBillPayment payment) throws BillNotFoundException, PaymentException;
    public Optional<Bill> get(Long billId);
    public void update(Long billId, Bill electricityBill) throws BillNotFoundException;
    public void delete(long billId);
    public List<Bill> findAll();

}
