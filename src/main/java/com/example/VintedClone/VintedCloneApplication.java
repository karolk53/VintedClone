package com.example.VintedClone;

import com.example.VintedClone.product.Category;
import com.example.VintedClone.product.Product;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@SpringBootApplication
public class VintedCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(VintedCloneApplication.class, args);
	}




}
