package com.example.bill.controllers;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.bill.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

public class BillControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BillService billService;

    @InjectMocks
    private BillController billController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(billController).build();
    }

    @Test
    public void testPaySuccess() throws Exception {

        mockMvc.perform(post("/bills/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 100.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Successfull payment"));
    }

    @Test
    public void testPayBillNotFound() throws Exception {
        doThrow(new BillNotFoundException("Bill was not found")).when(billService).pay(anyLong(), any(Payment.class));

        mockMvc.perform(post("/bills/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 100.0}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Bill was not found"));
    }

    @Test
    public void testPayPaymentException() throws Exception {
        doThrow(new PaymentException("Payment error")).when(billService).pay(anyLong(), any(Payment.class));

        mockMvc.perform(post("/bills/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 100.0}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Payment error"));
    }

    @Test
    public void testGetBillSuccess() throws Exception {
        Bill bill = new ElectricityBill();
        // Set bill properties if needed
        when(billService.get(anyLong())).thenReturn(Optional.of(bill));

        mockMvc.perform(get("/bills/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // Add additional assertions for the bill properties if needed
    }

    @Test
    public void testGetBillNotFound() throws Exception {
        when(billService.get(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/bills/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Bill was not found"));
    }
}
