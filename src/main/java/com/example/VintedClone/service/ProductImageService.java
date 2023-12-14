package com.example.VintedClone.service;

import com.example.VintedClone.model.ProductImage;
import com.example.VintedClone.repository.ProductImageRepositroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ProductImageService {

    private final ProductImageRepositroy productImageRepositroy;
    //private final String folderPath = "C:\\Users\\limik\\Desktop\\java\\vintedClone\\data\\productImages";
    private final String folderPath = "C:\\Users\\karol\\Desktop\\JAVA\\VintedClone\\productImages";

    @Autowired
    public ProductImageService(ProductImageRepositroy productImageRepositroy) {
        this.productImageRepositroy = productImageRepositroy;
    }

    public String uploadImage(MultipartFile file, Long productId) throws IOException {
        String fileName = file.getOriginalFilename();
        String filePath = folderPath + File.separator + fileName;

        ProductImage productImage = productImageRepositroy.save(
                ProductImage.builder()
                        .productId(productId)
                        .name(fileName)
                        .type(file.getContentType())
                        .filePath(filePath)
                        .build());

        File directory = new File(folderPath);
        if (!directory.exists()) {
            directory.mkdirs(); // Utwórz katalog, jeśli nie istnieje
        }

        file.transferTo(new File(filePath));

        if (productImage != null) {
            return "File uploaded successfully: " + filePath;
        }
        return null;
    }

    public List<ProductImage> getProductImages(Long productId) {
        return productImageRepositroy.findByProductId(productId);
    }

    public byte[] downloadImageFromFileSystem(int imageId) throws IOException {
        Optional<ProductImage> productImage = productImageRepositroy.findById(imageId);
        String filePath = productImage.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }


}
