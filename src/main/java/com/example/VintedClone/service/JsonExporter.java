package com.example.VintedClone.service;

import com.example.VintedClone.dto.ProductResponse;
import com.example.VintedClone.dto.PurchaseResponse;
import com.example.VintedClone.model.Product;

import java.util.List;

public interface JsonExporter{
    String export(List<PurchaseResponse> products);
}
