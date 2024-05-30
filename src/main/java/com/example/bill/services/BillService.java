package com.example.bill.services;

import java.util.Optional;

public interface BillService {

    public void pay(Long billId, Payment payment) throws BillNotFoundException, PaymentException;
    public Optional<Bill> get(Long billId);

}
