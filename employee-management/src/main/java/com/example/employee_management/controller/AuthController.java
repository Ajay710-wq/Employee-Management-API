package com.example.employee_management.controller;

import com.example.employee_management.dto.LoginRequest;
import com.example.employee_management.dto.LoginResponse;
import com.example.employee_management.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtService jwtService;

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request){
        if ("admin".equals(request.getUsername())
            &&
        "admin123".equals(
                request.getPassword())){
            String token= jwtService.generateToken(
                    request.getUsername(), "ADMIN"
            );
            return new LoginResponse(token);
        }
        if("user".equals(request.getUsername())
        &&
        "user123".equals(
                request.getPassword()
        )){
            String token=
                    jwtService.generateToken(
                            request.getUsername(), "USER"
                    );
            return new LoginResponse(token);
        }
        throw new RuntimeException("Invalid Credentials");
    }

}
