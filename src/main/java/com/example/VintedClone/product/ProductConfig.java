package com.example.VintedClone.product;

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
            Product p1 = new Product(
                    1L,
                    "TestProduct1",
                    "Description1",
                    Category.UBRANIA,
                    12,
                    LocalDate.of(2023, Month.JANUARY, 12)
            );
            Product p2 = new Product(
                    2L,
                    "TestProduct2",
                    "Description2",
                    Category.OBUWIE,
                    12,
                    LocalDate.of(2023, Month.JANUARY, 2)
            );
            Product p3 = new Product(
                    3L,
                    "TestProduct3",
                    "Description3",
                    Category.DODATKI,
                    12,
                    LocalDate.of(2023, Month.JANUARY, 1)
            );

            repository.saveAll(
                    List.of(p1,p2,p3)
            );
        };
    }
}
