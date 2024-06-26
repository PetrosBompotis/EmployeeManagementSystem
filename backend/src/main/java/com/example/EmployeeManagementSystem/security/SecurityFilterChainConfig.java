package com.example.EmployeeManagementSystem.security;

import com.example.EmployeeManagementSystem.jwt.JWTAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityFilterChainConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public SecurityFilterChainConfig(AuthenticationProvider authenticationProvider,
                                     JWTAuthenticationFilter jwtAuthenticationFilter,
                                     AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers(HttpMethod.POST,
                                        "/api/v1/customers",
                                        "/api/v1/auth/login",
                                        "/api/v1/auth/validate-token"
                                )
                                .permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/departments/{id}").hasAnyRole("USER", "SIMPLE_ADMIN", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1/departments").hasAnyRole("USER", "SIMPLE_ADMIN", "ADMIN")
                                .requestMatchers("/api/v1/employees/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/departments").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/departments/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/departments/{id}").hasRole("ADMIN")
                                .anyRequest()
                                .authenticated())
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint(authenticationEntryPoint));

        return http.build();
    }
}
