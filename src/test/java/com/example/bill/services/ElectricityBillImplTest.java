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
import com.example.bill.services.electricity.ElectricityBill;
import com.example.bill.services.electricity.ElectricityBillImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
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
    private ElectricityBill electricityBill;
    private ElectricityBillPayment payment;

    @BeforeEach
    public void setUp() {
        customer = new CustomerEntity();
        customer.setId(1L);
        billEntity = new ElectricityBillEntity();
        billEntity.setCustomer(customer);
        billEntity.setBalance(new BigDecimal("100.00"));

        electricityBill = new ElectricityBill();
        electricityBill.setBillId(1L);
        payment = new ElectricityBillPayment();
        payment.setBill(electricityBill);
        payment.setAmountToPay(new BigDecimal("50.00"));
    }

    @Test
    public void testPaySuccess() throws BillNotFoundException, PaymentException {
        when(billRepository.findByCustomerId(1L)).thenReturn(Optional.of(billEntity));

        electricityBillImpl.pay(payment);

        verify(billRepository, times(1)).findByCustomerId(1L);
        verify(billRepository, times(1)).save(billEntity);
        assertThat(billEntity.getBalance(), is(new BigDecimal("50.00")));
    }

    @Test
    public void testPayBillNotFound() {
        when(billRepository.findByCustomerId(anyLong())).thenReturn(Optional.empty());

        BillNotFoundException exception = assertThrows(BillNotFoundException.class, () -> {
            electricityBillImpl.pay(payment);
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
            electricityBillImpl.pay(payment);
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

    @Test
    public void testCreate() {
        ElectricityBillEntity billEntity = new ElectricityBillEntity();
        billEntity.setId(1L);
        billEntity.setTotalKhwConsumed(100.0);
        billEntity.setCostPerKhw(BigDecimal.valueOf(1.5));

        when(billMapper.billDtoToBillEntity(ArgumentMatchers.any(ElectricityBill.class))).thenReturn(billEntity);
        when(billRepository.save(ArgumentMatchers.any(ElectricityBillEntity.class))).thenReturn(billEntity);

        long billId = electricityBillImpl.create(electricityBill);

        assertThat(billId, is(1L));
        verify(billMapper, times(1)).billDtoToBillEntity(ArgumentMatchers.any(ElectricityBill.class));
        verify(billRepository, times(1)).save(ArgumentMatchers.any(ElectricityBillEntity.class));
    }

    @Test
    public void testUpdate() throws BillNotFoundException {
        ElectricityBillEntity billEntity = new ElectricityBillEntity();
        billEntity.setTotalKhwConsumed(100.0);
        billEntity.setCostPerKhw(BigDecimal.valueOf(1.5));

        when(billRepository.findById(anyLong())).thenReturn(Optional.of(billEntity));

        electricityBillImpl.update(1L, electricityBill);

        assertThat(billEntity.getTotalAmount(), is(BigDecimal.valueOf(150).setScale(2)));
        assertThat(billEntity.getBalance(), is(BigDecimal.valueOf(150).setScale(2)));
        verify(billMapper, times(1)).updateElectricityBillEntity(ArgumentMatchers.any(ElectricityBill.class), ArgumentMatchers.any(ElectricityBillEntity.class));
        verify(billRepository, times(1)).save(ArgumentMatchers.any(ElectricityBillEntity.class));
    }

    @Test
    public void testDelete() {
        doNothing().when(billRepository).deleteById(anyLong());

        electricityBillImpl.delete(1L);

        verify(billRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void testFindAll() {
        List<ElectricityBillEntity> billEntities = List.of(new ElectricityBillEntity(), new ElectricityBillEntity());
        when(billRepository.findAll()).thenReturn(billEntities);
        when(billMapper.billEntityToBill(ArgumentMatchers.any(ElectricityBillEntity.class))).thenReturn(new ElectricityBill());

        List<Bill> bills = electricityBillImpl.findAll();

        assertThat(bills.size(), is(2));
        verify(billRepository, times(1)).findAll();
        verify(billMapper, times(2)).billEntityToBill(ArgumentMatchers.any(ElectricityBillEntity.class));
    }
}
