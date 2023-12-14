package com.example.VintedClone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseResponse {
    private Long id;
    private String productName;
    private LocalDate purchaseDate;
    private float price;
}
