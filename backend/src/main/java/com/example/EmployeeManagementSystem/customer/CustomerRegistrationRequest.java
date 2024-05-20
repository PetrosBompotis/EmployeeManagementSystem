package com.example.EmployeeManagementSystem.customer;

public record CustomerRegistrationRequest(
        String name,
        String email,
        String password
) {
}

