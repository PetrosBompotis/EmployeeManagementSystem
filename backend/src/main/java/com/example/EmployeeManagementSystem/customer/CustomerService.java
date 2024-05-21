package com.example.EmployeeManagementSystem.customer;

import com.example.EmployeeManagementSystem.exception.DuplicateResourceException;
import com.example.EmployeeManagementSystem.exception.RequestValidationException;
import com.example.EmployeeManagementSystem.exception.ResourceNotFoundException;
import com.example.EmployeeManagementSystem.role.Role;
import com.example.EmployeeManagementSystem.role.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerDataAccessService customerDataAccessService;
    private final CustomerDTOMapper customerDTOMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public CustomerService(CustomerDataAccessService customerDataAccessService, CustomerDTOMapper customerDTOMapper, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.customerDataAccessService = customerDataAccessService;
        this.customerDTOMapper = customerDTOMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerDataAccessService.selectAllCustomers().stream()
                .map(customerDTOMapper)
                .collect(Collectors.toList());
    }

    public CustomerDTO getCustomer(Long id) {
        return customerDataAccessService.selectCustomerByID(id)
                .map(customerDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with id [%s] not found".formatted(id)
                ));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        String email = customerRegistrationRequest.getEmail();
        // Check if email exists
        if (customerDataAccessService.existsPersonWithEmail(email)) {
            throw new DuplicateResourceException("email already taken");
        }

        Role userRole = roleRepository.findByAuthority("ROLE_USER").get();
        List<Role> authorities = new ArrayList<>();
        authorities.add(userRole);

        // Otherwise add
        Customer customer = new Customer(
                customerRegistrationRequest.getName(),
                customerRegistrationRequest.getEmail(),
                passwordEncoder.encode(customerRegistrationRequest.getPassword()),
                authorities
        );
        customerDataAccessService.insertCustomer(customer);
    }

    public void deleteCustomerById(Long customerId) {
        // Check if id exists
        if (!customerDataAccessService.existsPersonWithId(customerId)) {
            throw new ResourceNotFoundException("Customer with id [%s] not found".formatted(customerId));
        }

        // Otherwise remove
        customerDataAccessService.deleteCustomerById(customerId);
    }

    public void updateCustomer(CustomerUpdateRequest updateRequest, Long customerId) {
        Customer customer = customerDataAccessService.selectCustomerByID(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with id [%s] not found".formatted(customerId)
                ));
        boolean changes = false;
        // Check if attributes need change exists
        if (updateRequest.getName() != null && !updateRequest.getName().equals(customer.getName())) {
            customer.setName(updateRequest.getName());
            changes = true;
        }

        if (updateRequest.getEmail() != null && !updateRequest.getEmail().equals(customer.getEmail())) {
            if (customerDataAccessService.existsPersonWithEmail(updateRequest.getEmail())) {
                throw new DuplicateResourceException("email already taken");
            }
            customer.setEmail(updateRequest.getEmail());
            changes = true;
        }
        // Otherwise update
        if (!changes) {
            throw new RequestValidationException("no data changes found");
        }

        customerDataAccessService.updateCustomerById(customer);
    }
}
