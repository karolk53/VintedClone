package com.example.VintedClone.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Purchase")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Purchase {
    @Id
    @GeneratedValue
    @Column(name = "purchase_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "purchase_id")
    private Product product;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDate creationDate;
}
