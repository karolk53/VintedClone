package com.example.VintedClone.product;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    public void addNewProduct(Product product){
        productRepository.save(product);
        //System.out.println(product);
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

}
