package com.example.VintedClone.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ProductImage")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private String name;
    private String type;
    private String filePath;

//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    private Product product;

    @Lob
    @Column(name = "productImage", length = 1000)
    private byte[] productImage;
}
