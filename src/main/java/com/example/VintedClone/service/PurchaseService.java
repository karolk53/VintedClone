package com.example.VintedClone.service;

import com.example.VintedClone.model.Product;
import com.example.VintedClone.model.Purchase;
import com.example.VintedClone.model.User;
import com.example.VintedClone.repository.ProductRepository;
import com.example.VintedClone.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository, ProductRepository productRepository) {
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;

    }

    public Purchase addPurchase(Long productId, User user) {
        Optional<Product> productOptional = productRepository.findProductById(String.valueOf(productId));

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            Purchase newPurchase = Purchase.builder()
                    .product(product)
                    .user(user)
                    .creationDate(new Date())
                    .build();

            return purchaseRepository.save(newPurchase);
        } else {
            throw new RuntimeException("Product not found for id: " + productId);
        }
    }


    public List<Purchase> getPurchasesByUserId(int userId) {
        // Pobieramy listę zakupów dla danego użytkownika
        return purchaseRepository.findByUserId(userId);
    }

    public List<Purchase> getPurchasesByDate(Date date) {
        // Pobieramy listę zakupów po dacie
        return purchaseRepository.findByCreationDateAfter(date);
    }
}
