package com.example.VintedClone.service;

import com.example.VintedClone.dto.ProductRequest;
import com.example.VintedClone.dto.ProductResponse;
import com.example.VintedClone.model.Category;
import com.example.VintedClone.model.Product;
import com.example.VintedClone.model.Status;
import com.example.VintedClone.model.User;
import com.example.VintedClone.repository.ProductRepository;
import com.example.VintedClone.repository.PurchaseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final PurchaseService purchaseService; // Dodaj PurchaseService


    public List<ProductResponse> getProducts(Category category){
        if(category != null){
            List<Product> products = productRepository.findProductsByCategory(category);
            return products.stream().map(this::mapToProductResponse).toList();
        }
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    public List<ProductResponse> getFilteredProducts(Category category, String description, Float price, String name) {
        List<Product> filteredProducts;

        if (category != null) {
            filteredProducts = productRepository.findProductsByCategory(category);
        } else {
            filteredProducts = productRepository.findAll();
        }

        // Dodaj filtry do wynikowej listy na podstawie warunków
        if (description != null) {
            filteredProducts = filteredProducts.stream()
                    .filter(product -> product.getDescription().contains(description))
                    .collect(Collectors.toList());
        }

        if (price != null) {
            filteredProducts = filteredProducts.stream()
                    .filter(product -> product.getPrice() <= price)
                    .collect(Collectors.toList());
        }

        if (name != null) {
            filteredProducts = filteredProducts.stream()
                    .filter(product -> product.getName().contains(name))
                    .collect(Collectors.toList());
        }

        return filteredProducts.stream()
                .map(this::mapToProductResponse)
                .toList();
    }


    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .price(product.getPrice())
                .added(product.getAdded())
                .status(product.getStatus().toString())
                .build();
    }

    public void addNewProduct(ProductRequest productRequest, User user){
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .category(productRequest.getCategory())
                .price(productRequest.getPrice())
                .added(LocalDate.now())
                .status(Status.AKUTALNE)
                .seller(user)
                .build();

        productRepository.save(product);
        log.info("Product " + product.getId() +" is saved");
    }

    public void deleteProduct(Long productId) {
      boolean exists =  productRepository.existsById(productId);

      if(!exists){
          throw new IllegalStateException("product with id " + productId + " does not exists");
      }
      productRepository.deleteById(productId);
    }

    @Transactional
    public void updateProduct(Long productId, String name, String description, String price, String categoryName) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalStateException("Product does not exist"));

        if (name != null && name.length() > 0 && !Objects.equals(product.getName(), name)) {
            product.setName(name);
        }

        if (description != null && description.length() > 0 && !Objects.equals(product.getDescription(), description)) {
            product.setDescription(description);
        }

        if (price != null && price.length() > 0 && !Objects.equals(product.getPrice(), Float.parseFloat(price))) {
            product.setPrice(Float.parseFloat(price));
        }

        if (categoryName != null && !Objects.equals(product.getCategory().name(), categoryName)) {
            try {
                Category category = Category.valueOf(categoryName.toUpperCase());
                product.setCategory(category);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid category name");
            }
        }

        productRepository.save(product);
    }

    public void buyProduct(Long productId, User user) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalStateException("Product does not exist"));
        product.setStatus(Status.SPRZEDANE);
        purchaseService.addPurchase(productId, user);
        productRepository.save(product);
    }

    public List<ProductResponse> getProductsForUser(User user) {
        List<Product> products = productRepository.findProductsBySeller(user);

        return products.stream().map(this::mapToProductResponse).toList();
    }

}
