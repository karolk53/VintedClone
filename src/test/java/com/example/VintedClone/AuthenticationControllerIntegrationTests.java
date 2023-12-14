package com.example.VintedClone;

import com.example.VintedClone.auth.AuthenticationRequest;
import com.example.VintedClone.dto.RegisterRequest;
import com.example.VintedClone.model.Product;
import com.example.VintedClone.model.Role;
import com.example.VintedClone.model.User;
import com.example.VintedClone.repository.ProductRepository;
import com.example.VintedClone.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = VintedCloneApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@AutoConfigureTestDatabase
public class AuthenticationControllerIntegrationTests {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void WhenValidInput_ThenRegister() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        RegisterRequest user = new RegisterRequest("Jan", "Kowalski", "jan@example.com", "password");
        mvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
        ).andExpect(status().isOk());
    }

    @Test
    public void Login_ForGivenAuthenticationRequest_ReturnToken() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("jan@example.com", "password");
        mvc.perform(post("/api/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpect(status().isOk());
    }
}