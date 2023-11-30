package com.example.VintedClone.controller;

import com.example.VintedClone.dto.ProductRequest;
import com.example.VintedClone.dto.ProductResponse;
import com.example.VintedClone.model.Product;
import com.example.VintedClone.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getCurrentProducts(){
        return productService.getProducts();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewProduct(@RequestBody ProductRequest productRequest){
        productService.addNewProduct(productRequest);
    }

    @DeleteMapping(path = "{productId}")
    public void deleteProduct(@PathVariable("productId") Long productId){
        productService.deleteProduct(productId);
    }

    @PutMapping(path = "{productId}")
    public void updateProduct(@PathVariable("productId") Long productId,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String description,
                              @RequestParam(required = false) String price,
                              @RequestParam(required = false) String categoryName
    ){
        productService.updateProduct(productId , name, description, price, categoryName);
    }

    @PutMapping(path = "/buy/{productId}")
    public void buyProduct(@PathVariable("productId") Long productId){
        productService.buyProduct(productId);
    }
}
