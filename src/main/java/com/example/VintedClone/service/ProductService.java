package com.example.VintedClone.service;

import com.example.VintedClone.dto.ProductRequest;
import com.example.VintedClone.dto.ProductResponse;
import com.example.VintedClone.model.Category;
import com.example.VintedClone.model.Product;
import com.example.VintedClone.model.Status;
import com.example.VintedClone.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getProducts(){
        List<Product> products = productRepository.findAll();   //findProductsByStatus(Status.AKUTALNE.toString());
        return products.stream().map(this::mapToProductResponse).toList();
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

    public void addNewProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .category(productRequest.getCategory())
                .price(productRequest.getPrice())
                .added(LocalDate.now())
                .status(Status.AKUTALNE)
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

        System.out.println("t1" + name);
        System.out.println("t2" + description);
        System.out.println("t3" + price);
        System.out.println("t4" + categoryName);
        productRepository.save(product);
    }

    public void buyProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalStateException("Product does not exist"));
        product.setStatus(Status.SPRZEDANE);
        productRepository.save(product);
    }

}
