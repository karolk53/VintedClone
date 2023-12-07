package com.example.VintedClone.controller;

import com.example.VintedClone.model.Purchase;
import com.example.VintedClone.model.User;
import com.example.VintedClone.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping("/add")
    public ResponseEntity<Purchase> addPurchase(@RequestParam Long productId, @RequestBody User user) {
        try {
            Purchase newPurchase = purchaseService.addPurchase(productId, user);
            return new ResponseEntity<>(newPurchase, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Purchase>> getPurchasesByUserId(@PathVariable Long userId) {
        List<Purchase> purchases = purchaseService.getPurchasesByUserId(Math.toIntExact(userId));
        return ResponseEntity.ok(purchases);
    }

    @GetMapping("/date")
    public ResponseEntity<List<Purchase>> getPurchasesByDate(@RequestParam Date date) {
        List<Purchase> purchases = purchaseService.getPurchasesByDate(date);
        return ResponseEntity.ok(purchases);
    }
}
