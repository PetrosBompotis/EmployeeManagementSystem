package com.example.EmployeeManagementSystem.customer;

import java.util.List;

public record CustomerDTO(
        Long id,
        String name,
        String email,
        List<String> roles,
        String username
) {
}
