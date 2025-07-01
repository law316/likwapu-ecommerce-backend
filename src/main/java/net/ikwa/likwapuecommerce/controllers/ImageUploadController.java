package net.ikwa.likwapuecommerce.controllers;

import net.ikwa.likwapuecommerce.model.ProductModel;
import net.ikwa.likwapuecommerce.repository.ProductRepository; // Assume you have this repository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ImageUploadController {

    @Autowired
    private ProductRepository productRepository;

    private static String UPLOAD_DIR = "uploads/";

    @PostMapping("/products/{id}/image")
    public ResponseEntity<String> uploadProductImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        // 1. Validate file type/size
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file selected.");
        }

        // (Optional) Check file type, e.g., only allow JPEG/PNG
        String fileType = file.getContentType();
        if (!fileType.equals("image/jpeg") && !fileType.equals("image/png")) {
            return ResponseEntity.badRequest().body("Only JPEG or PNG allowed.");
        }

        // 2. Save file to local disk
        try {
            // Make sure the directory exists
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Create a unique filename
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.write(filePath, file.getBytes());

            // 3. Get the file URL or path
            String imageUrl = "/uploads/" + fileName; // URL accessible from frontend (see below)

            // 4. Update product record with image URL
            ProductModel product = productRepository.findById(id).orElse(null);
            if (product == null) {
                return ResponseEntity.notFound().build();
            }
            product.setImageUrl(imageUrl);
            productRepository.save(product);

            // 5. Return success response (maybe the new image URL)
            return ResponseEntity.ok(imageUrl);

        } catch (IOException e) {
            return ResponseEntity.status(500).body("Image upload failed.");
        }
    }
}
