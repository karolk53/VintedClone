package com.example.VintedClone.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("select p from Product p WHERE p.id = ?1")
    Optional<Product> findProductById(String id);
}
