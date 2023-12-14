package com.example.VintedClone.repository;

import com.example.VintedClone.model.Category;
import com.example.VintedClone.model.Product;
import com.example.VintedClone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("select p from Product p WHERE p.id = ?1")
    Optional<Product> findProductById(String id);

    @Query("select p from Product p where p.status = ?1")
    List<Product> findProductsByStatus(String status);

    @Query("select p from Product p where p.category = ?1")
    List<Product> findProductsByCategory(Category category);

    @Query("select p from Product p where p.seller = ?1")
    List<Product> findProductsBySeller(User seller);

}
