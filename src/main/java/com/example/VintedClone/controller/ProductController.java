package com.example.VintedClone.controller;

import com.example.VintedClone.dto.ProductRequest;
import com.example.VintedClone.dto.ProductResponse;
import com.example.VintedClone.model.Product;
import com.example.VintedClone.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/product")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getCurrentProducts(){
        return productService.getProducts();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new product")
    public void addNewProduct(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Product add",
                required = true,
                content = @Content(schema = @Schema(implementation = ProductRequest.class))
            )
            @Valid
            @RequestBody ProductRequest productRequest
    ){
        productService.addNewProduct(productRequest);
    }

    @DeleteMapping(path = "{productId}")
    @Operation(description = "Delete product")
    public void deleteProduct(@PathVariable("productId") Long productId){
        productService.deleteProduct(productId);
    }

    @PutMapping(path = "{productId}")
    @Operation(summary = "Update product info")
    public void updateProduct(@PathVariable("productId") Long productId,
                              @RequestBody(required = false) String name,
                              @RequestBody(required = false) String description,
                              @RequestBody(required = false) String price,
                              @RequestBody(required = false) String categoryName
    ){
        productService.updateProduct(productId , name, description, price, categoryName);
    }

    @PutMapping(path = "/buy/{productId}")
    @Operation(summary = "Buy product")
    public void buyProduct(@PathVariable("productId") Long productId){
        productService.buyProduct(productId);
    }
}
