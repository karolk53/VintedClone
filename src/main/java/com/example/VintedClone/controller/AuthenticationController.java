package com.example.VintedClone.controller;

import com.example.VintedClone.auth.AuthenticationRequest;
import com.example.VintedClone.auth.AuthenticationResponse;
import com.example.VintedClone.dto.RegisterRequest;
import com.example.VintedClone.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }

}
