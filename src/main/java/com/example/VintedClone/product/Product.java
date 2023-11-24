package com.example.VintedClone.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "products") // Dodaj nazwę tabeli, w której mają być przechowywane produkty
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    private long id;

    private String name;
    private String description;

    @Enumerated(EnumType.STRING) // Dodaj adnotację do obsługi typu wyliczeniowego Category
    private Category category;

    private float price;

    @Column(name = "added_date") // Dodaj nazwę kolumny w bazie danych
    private LocalDate added;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", added=" + added +
                '}';
    }
}
