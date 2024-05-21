package com.example.EmployeeManagementSystem.customer;

import java.util.List;

public class CustomerDTO {
    private Long id;
    private String name;
    private String email;
    private List<String> roles;
    private String username;

    public CustomerDTO(Long id, String name, String email, List<String> roles, String username) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.roles = roles;
        this.username = username;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
