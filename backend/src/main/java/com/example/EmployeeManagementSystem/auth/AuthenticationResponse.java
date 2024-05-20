package com.example.EmployeeManagementSystem.auth;

import com.example.EmployeeManagementSystem.customer.CustomerDTO;

public record AuthenticationResponse(
        String token,
        CustomerDTO customerDTO
) {
}
