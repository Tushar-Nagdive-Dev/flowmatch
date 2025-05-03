package com.inn.smart_reconciliation_api.auth;

import com.inn.smart_reconciliation_api.auditTrail.services.AuditTrailService;
import com.inn.smart_reconciliation_api.configs.security.JwtUtil;
import com.inn.smart_reconciliation_api.dtos.LoginRequest;
import com.inn.smart_reconciliation_api.dtos.LoginResponse;
import com.inn.smart_reconciliation_api.dtos.RegisterRequest;
import com.inn.smart_reconciliation_api.entities.Role;
import com.inn.smart_reconciliation_api.entities.Users;
import com.inn.smart_reconciliation_api.repo.RoleRepository;
import com.inn.smart_reconciliation_api.repo.UserRepository;
import com.inn.smart_reconciliation_api.services.impl.AuthServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AuditTrailService auditTrailService;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister_Success() {
        RegisterRequest request = new RegisterRequest("newuser", "newuser@mail.com", "password");

        Role userRole = new Role();
        userRole.setName("USER");

        when(roleRepository.findByName("USER")).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode(request.password())).thenReturn("encodedPassword");

        authService.register(request);

        verify(userRepository, times(1)).save(any(Users.class));
    }

    @Test
    void testRegister_RoleNotFound() {
        RegisterRequest request = new RegisterRequest("newuser", "newuser@mail.com", "password");

        when(roleRepository.findByName("USER")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            authService.register(request);
        });

        assertEquals("Default role not found", ex.getMessage());
    }

    @Test
    void testLogin_Success() {
        LoginRequest request = new LoginRequest("existingUser", "password");
        Users user = new Users();
        user.setUsername("existingUser");

        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(user)).thenReturn("mock-jwt");

        // AuthenticationManager will be called → No need to mock behavior → just verify
        LoginResponse response = authService.login(request);

        assertEquals("mock-jwt", response.token());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(auditTrailService).recordAction(any(), any(), any(), any(), any());
    }

    @Test
    void testLogin_UserNotFound() {
        LoginRequest request = new LoginRequest("invalidUser", "password");

        when(userRepository.findByUsername("invalidUser")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            authService.login(request);
        });
    }
}
