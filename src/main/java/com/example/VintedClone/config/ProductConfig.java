package com.example.VintedClone.config;

import com.example.VintedClone.model.Category;
import com.example.VintedClone.model.Product;
import com.example.VintedClone.model.Status;
import com.example.VintedClone.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class ProductConfig {
    @Bean
    CommandLineRunner commandLineRunner(ProductRepository repository){
        return args -> {
        };
    }
}
