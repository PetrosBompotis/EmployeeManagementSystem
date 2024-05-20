package com.example.EmployeeManagementSystem.customer;

public record CustomerUpdateRequest(
        String name,
        String email
) {
}
