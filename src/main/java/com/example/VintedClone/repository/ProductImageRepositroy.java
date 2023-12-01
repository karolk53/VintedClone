package com.example.VintedClone.repository;

import com.example.VintedClone.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepositroy extends JpaRepository<ProductImage, Integer> {
    List<ProductImage> findByProductId(Long productId);

}
