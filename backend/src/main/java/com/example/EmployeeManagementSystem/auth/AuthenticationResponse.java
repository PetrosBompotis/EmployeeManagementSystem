package com.example.EmployeeManagementSystem.auth;

import com.example.EmployeeManagementSystem.customer.CustomerDTO;

public class AuthenticationResponse {
    private String token;
    private CustomerDTO customerDTO;

    public AuthenticationResponse(String token, CustomerDTO customerDTO) {
        this.token = token;
        this.customerDTO = customerDTO;
    }

    public String getToken() {
        return token;
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }
}