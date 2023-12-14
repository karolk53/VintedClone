package com.example.VintedClone.service;

import com.example.VintedClone.dto.PurchaseResponse;
import com.example.VintedClone.model.Product;
import com.example.VintedClone.model.Purchase;
import com.example.VintedClone.model.User;
import com.example.VintedClone.repository.ProductRepository;
import com.example.VintedClone.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalStateException("Product does not exist"));
        System.out.println(product);
        System.out.println(user);

        Purchase newPurchase = Purchase.builder()
                .product(product)
                .user(user)
                .creationDate(LocalDate.now())
                .build();

        return purchaseRepository.save(newPurchase);
    }


    public List<PurchaseResponse> getPurchasesByUserId(int userId) {
        // Pobieramy listę zakupów dla danego użytkownika
        List<Purchase> purchases = purchaseRepository.findByUserId(userId);
        return purchases.stream().map(this::mapToPurchaseResponse).toList();
    }

    public List<PurchaseResponse> getPurchasesByDate(LocalDate date) {
        // Pobieramy listę zakupów po dacie
        List<Purchase> purchases = purchaseRepository.findByCreationDateAfter(date);
        return purchases.stream().map(this::mapToPurchaseResponse).toList();
    }

    private PurchaseResponse mapToPurchaseResponse(Purchase purchase) {
        return PurchaseResponse.builder()
                .id(purchase.getId())
                .productName(purchase.getProduct().getName())
                .purchaseDate(purchase.getCreationDate())
                .price(purchase.getProduct().getPrice())
                .build();

    }
}
