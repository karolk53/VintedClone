package com.example.VintedClone.controller;

import com.example.VintedClone.dto.PurchaseResponse;
import com.example.VintedClone.model.Purchase;
import com.example.VintedClone.model.User;
import com.example.VintedClone.service.PurchaseService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

//    @PostMapping("/add/{productId}")
//    public ResponseEntity<Purchase> addPurchase(@AuthenticationPrincipal User user, @PathVariable Long productId) {
//        try {
//            Purchase newPurchase = purchaseService.addPurchase(productId, user);
//            return new ResponseEntity<>(newPurchase, HttpStatus.CREATED);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping("/user")
    public ResponseEntity<List<PurchaseResponse>> getPurchasesByUserId(@AuthenticationPrincipal User user) {
        List<PurchaseResponse> purchases = purchaseService.getPurchasesByUserId(user.getId());
        return ResponseEntity.ok(purchases);
    }

    @GetMapping("/date")
    public ResponseEntity<List<PurchaseResponse>> getPurchasesByDate(@RequestParam LocalDate date) {
        List<PurchaseResponse> purchases = purchaseService.getPurchasesByDate(date);
        return ResponseEntity.ok(purchases);
    }
}
