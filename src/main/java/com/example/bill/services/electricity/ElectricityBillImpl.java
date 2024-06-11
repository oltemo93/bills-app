package com.example.bill.services.electricity;

import com.example.bill.mappers.BillMapper;
import com.example.bill.repositories.BillRepository;
import com.example.bill.repositories.entities.ElectricityBillEntity;
import com.example.bill.services.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ElectricityBillImpl implements BillService {

    private BillRepository billRepository;
    private BillMapper billMapper;

    public ElectricityBillImpl(BillRepository billRepository, BillMapper billMapper) {
        this.billRepository = billRepository;
        this.billMapper = billMapper;
    }

    @Override
    public long create(Bill bill) {
        ElectricityBillEntity billEntity = billMapper.billDtoToBillEntity((ElectricityBill) bill);
        BigDecimal amount = OutstandingDebtCalculator.calculate(billEntity.getTotalKhwConsumed(), billEntity.getCostPerKhw());
        billEntity.setTotalAmount(amount);
        billEntity.setBalance(amount);
        ElectricityBillEntity result = billRepository.save(billEntity);
        return result.getId();
    }

    @Override
    public void pay(ElectricityBillPayment payment) throws BillNotFoundException, PaymentException {
        Long billId = payment.getBill().getBillId();
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

    @Override
    public void update(Long billId, Bill electricityBill) throws BillNotFoundException {
        ElectricityBillEntity bill = this.billRepository
                .findById(billId)
                .orElseThrow(() -> new BillNotFoundException("Bill was not found"));

        billMapper.updateElectricityBillEntity((ElectricityBill) electricityBill, bill);
        BigDecimal amount = OutstandingDebtCalculator.calculate(bill.getTotalKhwConsumed(), bill.getCostPerKhw());
        bill.setTotalAmount(amount);
        bill.setBalance(amount);
        billRepository.save(bill);

    }

    @Override
    public void delete(long billId) {
        billRepository.deleteById(billId);
    }

    @Override
    public List<Bill> findAll() {
        return billRepository.findAll().stream().map(billMapper::billEntityToBill).collect(Collectors.toList());
    }

}
