package com.inn.smart_reconciliation_api.services.impl;

import java.time.Instant;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.inn.smart_reconciliation_api.auditTrail.enums.AuditAction;
import com.inn.smart_reconciliation_api.auditTrail.enums.AuditEntityType;
import com.inn.smart_reconciliation_api.auditTrail.services.AuditTrailService;
import com.inn.smart_reconciliation_api.configs.security.JwtUtil;
import com.inn.smart_reconciliation_api.dtos.LoginRequest;
import com.inn.smart_reconciliation_api.dtos.LoginResponse;
import com.inn.smart_reconciliation_api.dtos.RegisterRequest;
import com.inn.smart_reconciliation_api.entities.Role;
import com.inn.smart_reconciliation_api.entities.Users;
import com.inn.smart_reconciliation_api.repo.RoleRepository;
import com.inn.smart_reconciliation_api.repo.UserRepository;
import com.inn.smart_reconciliation_api.services.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    private final AuditTrailService auditTrailService;
    
    @Override
    public void register(RegisterRequest request) {
        Role userRole = roleRepository.findByName("USER")
            .orElseThrow(() -> new RuntimeException("Default role not found"));

        Users user = new Users();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEnabled(true);
        user.getRoles().add(userRole);
        user.setCreatedTime(Instant.now());
        user.setCreatorId(0L);
        user.setCreatorUsername("FLOWMATCH_SYSTEM");
        user.setLastModifiedTime(null);
        user.setLastModifierId(null);
        user.setLastModifierUsername("NA");
        userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
        Users user = userRepository.findByUsername(request.username()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String jwt = jwtUtil.generateToken(user);
        auditTrailService.recordAction(AuditEntityType.AUTH, user.getId(), AuditAction.LOGIN, user.getId(), user.getUsername());
        return new LoginResponse(jwt);
    }
}
