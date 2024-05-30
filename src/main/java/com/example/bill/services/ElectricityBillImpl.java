package com.example.bill.services;

import com.example.bill.mappers.BillMapper;
import com.example.bill.repositories.BillRepository;
import com.example.bill.repositories.entities.ElectricityBillEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ElectricityBillImpl implements BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillMapper billMapper;

    @Override
    public void pay(Long billId, Payment payment) throws BillNotFoundException, PaymentException {
        ElectricityBillEntity billDB = billRepository.findByCustomerId(billId)
                .orElseThrow(() -> new BillNotFoundException("Bill with given ID was not found"));
        BigDecimal currentBalance = billDB.getBalance();
        if (currentBalance.compareTo(BigDecimal.ZERO) <= 0) {
            throw new PaymentException("No outstanding balance to pay");
        }
        BigDecimal newBalance = currentBalance.subtract(payment.getAmountToPay());

        billDB.setBalance(newBalance);
        billRepository.save(billDB);

    }

    @Override
    public Optional<Bill> get(Long billId) {
        return billRepository.findById(billId)
                .map(billMapper::billEntityToBill);

    }

}
