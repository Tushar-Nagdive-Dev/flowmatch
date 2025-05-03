# FlowMatch - Auth Module Testing Plan

## Overview

This document defines the test coverage for the FlowMatch Smart Reconciliation Auth module.

### Components Covered

- LoginComponent
- RegisterComponent
- AuthService
- AuthInterceptor (HttpInterceptorFn)

## Test Cases

| Area | Test Case | Test file | Description |
|------|-----------|-----------|-------------|
| LoginComponent | Should create component | login.component.spec.ts | Verify component initializes properly |
| LoginComponent | Should login successfully | login.component.spec.ts | Simulate login success and verify |
| LoginComponent | Should handle login error | login.component.spec.ts | Simulate login error and show message |
| RegisterComponent | Should create component | register.component.spec.ts | Verify component initializes properly |
| RegisterComponent | Should register successfully | register.component.spec.ts | Simulate registration success |
| RegisterComponent | Should handle register error | register.component.spec.ts | Simulate registration error and show message |
| AuthService | Should create service | auth.service.spec.ts | Service created properly |
| AuthService | Should login successfully | auth.service.spec.ts | Simulate login API call |
| AuthService | Should handle login error | auth.service.spec.ts | Simulate login error |
| AuthService | Should register successfully | auth.service.spec.ts | Simulate register API call |
| AuthInterceptor | Should add Authorization header if token exists | auth.interceptor.spec.ts | Set token → header should be added |
| AuthInterceptor | Should NOT add Authorization header if no token exists | auth.interceptor.spec.ts | No token → no header added |

## Environment Management

| File | Description |
|------|-------------|
| environment.ts | Dev backend URL configuration |
| environment.prod.ts | Production backend URL |

## Notes

- HTTP calls are fully mocked → fast and isolated tests
- Success + Error paths → both tested
- Easy to extend
- CI/CD Friendly → no backend dependency

