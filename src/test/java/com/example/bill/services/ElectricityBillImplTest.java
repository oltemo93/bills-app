package com.example.bill.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import com.example.bill.repositories.BillRepository;
import com.example.bill.repositories.entities.BillEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ElectricityBillImplTest {

    @Mock
    private BillRepository billRepository;

    @InjectMocks
    private ElectricityBillImpl electricityBillImpl;

    private Payment payment;
    private BillEntity billEntity;

    @BeforeEach
    public void setUp() {
        Customer customer = new Customer();
        customer.setCustomerId(12345L);

        Bill bill = new ElectricityBill();
        bill.setCustomer(customer);

        payment = new Payment();
        payment.setBill(bill);
        payment.setAmountToPay(new BigDecimal("50.00"));

        billEntity = new BillEntity();
        billEntity.setCustomerId(12345L);
        billEntity.setBalance(new BigDecimal("100.00"));
    }

    @Test
    public void testPaySuccess() throws CustomerNotFoundException {
        when(billRepository.findByCustomerId(12345L)).thenReturn(Optional.of(billEntity));

        electricityBillImpl.pay(payment);

        verify(billRepository, times(1)).findByCustomerId(12345L);
        verify(billRepository, times(1)).save(billEntity);
        assertEquals(new BigDecimal("50.00"), billEntity.getBalance());
    }

    @Test
    public void testPayCustomerNotFound() {
        when(billRepository.findByCustomerId(12345L)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> {
            electricityBillImpl.pay(payment);
        });

        verify(billRepository, times(1)).findByCustomerId(12345L);
        verify(billRepository, never()).save(any(BillEntity.class));
    }
}