package com.example.VintedClone.service;

import com.example.VintedClone.auth.AuthenticationRequest;
import com.example.VintedClone.auth.AuthenticationResponse;
import com.example.VintedClone.dto.RegisterRequest;
import com.example.VintedClone.model.Role;
import com.example.VintedClone.model.User;
import com.example.VintedClone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtSerivce;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User
                .builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtSerivce.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );
        var user = repository.findByEmail(request.getEmail()).orElseThrow();

        var jwtToken = jwtSerivce.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
