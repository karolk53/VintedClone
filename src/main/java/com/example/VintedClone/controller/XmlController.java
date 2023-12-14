package com.example.VintedClone.controller;

import com.example.VintedClone.dto.ProductResponse;
import com.example.VintedClone.dto.ProductResponseList;
import com.example.VintedClone.model.Category;
import com.example.VintedClone.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.io.File;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/xml")
@RequiredArgsConstructor
//@SecurityRequirement(name = "bearerAuth")
@Tag(name = "XML Files")
public class XmlController {

    private final ProductService productService;

    @GetMapping("/download")
    @Operation(summary = "Download list of products by category to XML file")
    public ResponseEntity<?> getXmlFileWithProducts(@RequestParam(required = false) Category cateogry) throws JAXBException, FileNotFoundException {
        JAXBContext ctx = JAXBContext.newInstance(ProductResponseList.class);
        Marshaller marshaller = ctx.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        List<ProductResponse> products = productService.getProducts(cateogry);
        ProductResponseList productResponseList = new ProductResponseList();
        productResponseList.setProductResponseList(products);
        File file = new File("products.xml");
        marshaller.marshal(productResponseList, file);

        InputStream inputStream = new FileInputStream("products.xml");

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=products.xml")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(inputStream));
    }

//    @PostMapping
//    public ResponseEntity<String> importProductsFromXmlFile(@RequestParam("file")MultipartFile file){
//
//    }
}
