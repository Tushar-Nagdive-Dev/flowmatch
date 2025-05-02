package com.inn.smart_reconciliation_api.payment;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inn.smart_reconciliation_api.configs.entities.exception.GlobalExceptionHandler;
import com.inn.smart_reconciliation_api.controllers.PaymentController;
import com.inn.smart_reconciliation_api.dtos.PaymentRequest;
import com.inn.smart_reconciliation_api.dtos.PaymentResponse;
import com.inn.smart_reconciliation_api.enums.PaymentStatus;
import com.inn.smart_reconciliation_api.services.PaymentService;
import com.inn.smart_reconciliation_api.services.impl.CustomUserDetailsService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
@Import({PaymentController.class, GlobalExceptionHandler.class})
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setupSecurity() {
        when(customUserDetailsService.loadUserByUsername(any()))
            .thenReturn(User.withUsername("testuser").password("password").roles("USER").build());
    }

    @Test
    void testCreatePayment() throws Exception {
        PaymentRequest request = new PaymentRequest("PAY-001", BigDecimal.valueOf(200), PaymentStatus.PENDING, LocalDate.now(), "Payer A");
        PaymentResponse response = new PaymentResponse(1L, "PAY-001", BigDecimal.valueOf(200), PaymentStatus.SUCCESS, LocalDate.now(), "Payer A");

        when(paymentService.createPayment(any())).thenReturn(response);

        mockMvc.perform(post("/api/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPayment() throws Exception {
        when(paymentService.getPayment(1L)).thenReturn(new PaymentResponse(1L, "PAY-001", BigDecimal.valueOf(200), PaymentStatus.SUCCESS, LocalDate.now(), "Payer A"));

        mockMvc.perform(get("/api/payments/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllPayments() throws Exception {
        when(paymentService.getAllPayments()).thenReturn(List.of());

        mockMvc.perform(get("/api/payments"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdatePayment() throws Exception {
        PaymentRequest request = new PaymentRequest("PAY-001", BigDecimal.valueOf(200), PaymentStatus.PENDING, LocalDate.now(), "Payer A");

        when(paymentService.updatePayment(eq(1L), any())).thenReturn(new PaymentResponse(1L, "PAY-001", BigDecimal.valueOf(200), PaymentStatus.PENDING, LocalDate.now(), "Payer A"));

        mockMvc.perform(put("/api/payments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeletePayment() throws Exception {
        mockMvc.perform(delete("/api/payments/1"))
                .andExpect(status().isOk());
    }
}
