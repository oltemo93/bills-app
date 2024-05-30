package com.example.bill.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com.example.bill.mappers.BillMapper;
import com.example.bill.repositories.BillRepository;
import com.example.bill.repositories.entities.CustomerEntity;
import com.example.bill.repositories.entities.ElectricityBillEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ElectricityBillImplTest {

    @Mock
    private BillRepository billRepository;

    @Mock
    private BillMapper billMapper;

    @InjectMocks
    private ElectricityBillImpl electricityBillImpl;

    private CustomerEntity customer;
    private ElectricityBillEntity billEntity;
    private Payment payment;

    @BeforeEach
    public void setUp() {
        customer = new CustomerEntity();
        customer.setId(1L);
        billEntity = new ElectricityBillEntity();
        billEntity.setCustomer(customer);
        billEntity.setBalance(new BigDecimal("100.00"));

        payment = new Payment();
        payment.setAmountToPay(new BigDecimal("50.00"));
    }

    @Test
    public void testPaySuccess() throws BillNotFoundException, PaymentException {
        when(billRepository.findByCustomerId(1L)).thenReturn(Optional.of(billEntity));

        electricityBillImpl.pay(1L, payment);

        verify(billRepository, times(1)).findByCustomerId(1L);
        verify(billRepository, times(1)).save(billEntity);
        assertThat(billEntity.getBalance(), is(new BigDecimal("50.00")));
    }

    @Test
    public void testPayBillNotFound() {
        when(billRepository.findByCustomerId(anyLong())).thenReturn(Optional.empty());

        BillNotFoundException exception = assertThrows(BillNotFoundException.class, () -> {
            electricityBillImpl.pay(1L, payment);
        });

        assertThat(exception.getMessage(), is("Bill with given ID was not found"));
        verify(billRepository, times(1)).findByCustomerId(1L);
        verify(billRepository, never()).save(ArgumentMatchers.any(ElectricityBillEntity.class));
    }

    @Test
    public void testPayNoOutstandingBalance() {
        billEntity.setBalance(BigDecimal.ZERO);
        when(billRepository.findByCustomerId(1L)).thenReturn(Optional.of(billEntity));

        PaymentException exception = assertThrows(PaymentException.class, () -> {
            electricityBillImpl.pay(1L, payment);
        });

        assertThat(exception.getMessage(), is("No outstanding balance to pay"));
        verify(billRepository, times(1)).findByCustomerId(1L);
        verify(billRepository, never()).save(ArgumentMatchers.any(ElectricityBillEntity.class));
    }

    @Test
    public void testGetBillSuccess() {
        ElectricityBill bill = new ElectricityBill();

        when(billRepository.findById(1L)).thenReturn(Optional.of(billEntity));
        when(billMapper.billEntityToBill(billEntity)).thenReturn(bill);

        Optional<Bill> retrievedBill = electricityBillImpl.get(1L);

        assertThat(retrievedBill.isPresent(), is(true));
        assertThat(retrievedBill.get(), is(bill));
        verify(billRepository, times(1)).findById(1L);
        verify(billMapper, times(1)).billEntityToBill(billEntity);
    }

    @Test
    public void testGetBillNotFound() {
        when(billRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Bill> retrievedBill = electricityBillImpl.get(1L);

        assertThat(retrievedBill.isPresent(), is(false));
        verify(billRepository, times(1)).findById(1L);
        verify(billMapper, never()).billEntityToBill(ArgumentMatchers.any(ElectricityBillEntity.class));
    }
}
