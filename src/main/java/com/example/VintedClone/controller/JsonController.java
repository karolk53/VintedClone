package com.example.VintedClone.controller;

import com.example.VintedClone.dto.ProductRequest;
import com.example.VintedClone.dto.ProductResponse;
import com.example.VintedClone.dto.PurchaseResponse;
import com.example.VintedClone.model.User;
import com.example.VintedClone.service.JsonExporter;
import com.example.VintedClone.service.ProductService;
import com.example.VintedClone.service.PurchaseService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/api/json")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Json Files")
@Slf4j
public class JsonController {
    private final JsonExporter jsonExporter;
    private final ProductService productService;
    private final PurchaseService purchaseService;

    @GetMapping("/download")
    @Operation(summary = "Get list of purchases to JSON file")
    public ResponseEntity<byte[]> downloadPurchasesInJson(@AuthenticationPrincipal User user){
        List<PurchaseResponse> purchases = purchaseService.getPurchasesByUserId(user.getId());

        String productsJsonString = jsonExporter.export(purchases);

        byte[] productsJsonBytes = productsJsonString.getBytes();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=purchases.json")
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(productsJsonBytes.length)
                .body(productsJsonBytes);
    }

    @PostMapping("/import")
    @Operation(summary = "Import products from JSON file")
    public ResponseEntity<String> importProductsFromJson(@AuthenticationPrincipal User user, @RequestParam("file") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please provide a valid JSON file");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }

            Gson gson = new Gson();
            JsonArray jsonData = gson.fromJson(content.toString(), JsonArray.class);
            log.info("Imported " + String.valueOf(jsonData));
            for(JsonElement obj : jsonData){
                ProductRequest productRequest = gson.fromJson(obj, ProductRequest.class);
                productService.addNewProduct(productRequest, user);
            }


            return ResponseEntity.ok("Import successful");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error reading the file");
        }
    }
}
