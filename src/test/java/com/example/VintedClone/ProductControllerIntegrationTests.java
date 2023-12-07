package com.example.VintedClone;

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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = VintedCloneApplicationTests.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude= SecurityAutoConfiguration.class)
@AutoConfigureTestDatabase
public class ProductControllerIntegrationTests {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ProductRepository repository;
    @Autowired
    private UserRepository userRepository;

//    @Test
//    public void WhenValidInput_ThenRegister() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        mvc.perform(post("/api/auth/register")
//                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user)));
//        List<User> users = userRepository.findAll();
//        assertThat(users).extracting(User::getEmail).containsOnly("jan@example.com");
//    }
}