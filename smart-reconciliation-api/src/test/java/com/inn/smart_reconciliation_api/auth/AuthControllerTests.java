package com.inn.smart_reconciliation_api.auth;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inn.smart_reconciliation_api.configs.entities.exception.GlobalExceptionHandler;
import com.inn.smart_reconciliation_api.controllers.AuthController;
import com.inn.smart_reconciliation_api.dtos.LoginRequest;
import com.inn.smart_reconciliation_api.dtos.LoginResponse;
import com.inn.smart_reconciliation_api.dtos.RegisterRequest;
import com.inn.smart_reconciliation_api.services.AuthService;
import com.inn.smart_reconciliation_api.services.impl.CustomUserDetailsService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import({AuthController.class, GlobalExceptionHandler.class}) // ✅ Fix: Add Global Exception handler
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        when(customUserDetailsService.loadUserByUsername(any()))
            .thenReturn(User.withUsername("testuser").password("password").roles("USER").build());
    }

    @Test
    void testRegister() throws Exception {
        RegisterRequest request = new RegisterRequest("user", "user@mail.com", "password");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));
    }

    @Test
    void testLogin() throws Exception {
        LoginRequest request = new LoginRequest("user", "password");
        LoginResponse response = new LoginResponse("mock-jwt");

        when(authService.login(any(LoginRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mock-jwt"));
    }

    @Test
    void testLogin_userNotFound() throws Exception {
        // ✅ Mock Security (to not fail before reaching controller)
        when(customUserDetailsService.loadUserByUsername(any()))
            .thenReturn(User.withUsername("invalidUser").password("password").roles("USER").build());

        // ✅ Mock Service to throw Exception
        when(authService.login(any(LoginRequest.class)))
                .thenThrow(new UsernameNotFoundException("User not found"));

        LoginRequest request = new LoginRequest("invalidUser", "password");

        when(authService.login(any(LoginRequest.class)))
                .thenThrow(new UsernameNotFoundException("User not found"));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }
}
