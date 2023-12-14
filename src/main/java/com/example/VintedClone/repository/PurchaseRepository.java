package com.example.VintedClone.repository;

import com.example.VintedClone.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findByUserId(Integer user_id);
    List<Purchase> findByCreationDateAfter(LocalDate date);
}
