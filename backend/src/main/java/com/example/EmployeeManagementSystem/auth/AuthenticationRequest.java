package com.example.EmployeeManagementSystem.auth;

public record AuthenticationRequest(
        String username,
        String password
) {
}
