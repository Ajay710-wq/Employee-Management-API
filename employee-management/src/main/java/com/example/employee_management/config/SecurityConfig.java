package com.example.employee_management.config;

import com.example.employee_management.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtFilter;
    @Bean
    SecurityFilterChain securityFilterChain(
            HttpSecurity http)
        throws Exception{    //Who can access what?
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth-> // Every API requires login.
                        auth.requestMatchers("/auth/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/employees/**")
                                .hasAnyRole("ADMIN","USER")
                                .requestMatchers(HttpMethod.POST,"/employees/**")
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/employees/**")
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/employees/**")
                                .hasRole("ADMIN")
                                .anyRequest().authenticated())
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }
}
