package com.example.bill.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
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

        String requestBody = "{\n" +
                "    \"bill\":{\n" +
                "        \"type\":\"electricity\",\n" +
                "        \"customer\":{\n" +
                "            \"customerId\":1\n" +
                "        }\n" +
                "    },\n" +
                "    \"amountToPay\":100.0\n" +
                "}";

        mockMvc.perform(post("/bills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Successfull payment"));

        verify(billService).pay(any(Payment.class));
    }

    @Test
    public void testPayCustomerNotFound() throws Exception {
        String requestBody = "{\n" +
                "    \"bill\":{\n" +
                "        \"type\":\"electricity\",\n" +
                "        \"customer\":{\n" +
                "            \"customerId\":1\n" +
                "        }\n" +
                "    },\n" +
                "    \"amountToPay\":100.0\n" +
                "}";

        doThrow(new CustomerNotFoundException()).when(billService).pay(any(Payment.class));

        mockMvc.perform(post("/bills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Customer was not found"));

        verify(billService).pay(any(Payment.class));
    }
}