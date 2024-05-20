package com.example.EmployeeManagementSystem.auth;

public record TokenValidationRequest(
        String accessToken,
        String username
) {
}
