package com.example.EmployeeManagementSystem;

import com.example.EmployeeManagementSystem.customer.Customer;
import com.example.EmployeeManagementSystem.customer.CustomerRepository;
import com.example.EmployeeManagementSystem.role.Role;
import com.example.EmployeeManagementSystem.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class EmployeeManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementSystemApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, CustomerRepository customerRepository, PasswordEncoder passwordEncode){
		return args ->{
			if(roleRepository.findByAuthority("ROLE_ADMIN").isPresent()) return;
			Role adminRole = roleRepository.save(new Role("ROLE_ADMIN"));
			roleRepository.save(new Role("ROLE_USER"));

			List<Role> roles= new ArrayList<>();
			roles.add(adminRole);

			Customer admin = new Customer(
					"admin",
					"admin@gmail.com",
					passwordEncode.encode("123456789"),
					roles);

			customerRepository.save(admin);
		};
	}
}
