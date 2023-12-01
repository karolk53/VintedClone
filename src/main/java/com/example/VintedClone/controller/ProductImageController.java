package com.example.VintedClone.controller;

import com.example.VintedClone.model.ProductImage;
import com.example.VintedClone.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/productImage")
@RequiredArgsConstructor
public class ProductImageController {

    @Autowired
    private final ProductImageService productImageService;

    @PostMapping
    public ResponseEntity<String> addImage(@RequestParam("image") MultipartFile file) throws IOException {
        String productImage = productImageService.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK).body(productImage);
    }

//    @GetMapping(value = "/{productId}", produces = MediaType.IMAGE_PNG_VALUE)
//    public ResponseEntity<byte[]> getProductImages(@PathVariable Long productId) {
//        List<ProductImage> productImages = productImageService.getProductImages(productId);
//
//        System.out.println(productImages);
//
//        if (productImages.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        // Zakładając, że zawsze istnieje tylko jedno zdjęcie dla danego produktu
//        ProductImage productImage = productImages.get(0);
//
//        try {
//            File file = new File(productImage.getFilePath());
//            byte[] fileContent = Files.readAllBytes(file.toPath());
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.IMAGE_PNG);
//
//            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
//        } catch (IOException e) {
//            // Obsłuż błąd odczytu pliku
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    private ResponseEntity<byte[]> convertImageToResponse(ProductImage productImage) {
//        try {
//            File file = new File(productImage.getFilePath());
//            byte[] fileContent = Files.readAllBytes(file.toPath());
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.IMAGE_PNG); // Ustaw odpowiedni typ MIME dla obrazu
//
//            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }


    @GetMapping("/{imageId}")
    public ResponseEntity<?> getProductImages(@PathVariable Long imageId) {
        try {
            byte[] firstImage = productImageService.downloadImageFromFileSystem(Math.toIntExact(imageId));

            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(firstImage);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/all/{productId}")
    public ResponseEntity<?> getAllProductImages(@PathVariable Long productId) {
        List<ProductImage> productImages = productImageService.getProductImages(productId);

        List<String> imageUrls = productImages.stream()
                .map(productImage -> "http://localhost:8080/api/productImage/" + productImage.getId())
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(imageUrls);

    }




}
