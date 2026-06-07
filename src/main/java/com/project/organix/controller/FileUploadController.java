package com.project.organix.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.UUID;

@Controller
public class FileUploadController {

    @Value("${app.upload.dir:uploads/rewards}")
    private String uploadDir;

    /**
     * Handle upload gambar reward dari form multipart
     * Mengembalikan path URL gambar yang sudah disimpan
     */
    @PostMapping("/rewards/upload-image")
    @ResponseBody
    public String uploadRewardImage(@RequestParam("imageFile") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return "";
        }

        try {
            // Buat direktori jika belum ada
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate nama file unik agar tidak terjadi konflik
            String originalName = file.getOriginalFilename();
            String ext = "";
            if (originalName != null && originalName.contains(".")) {
                ext = originalName.substring(originalName.lastIndexOf("."));
            }
            String fileName = UUID.randomUUID() + ext;

            // Simpan file
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Kembalikan URL path yang bisa diakses browser
            return "/uploads/rewards/" + fileName;

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Serve file gambar yang sudah diupload
     */
    @GetMapping("/uploads/rewards/{filename:.+}")
    public ResponseEntity<Resource> serveRewardImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            // Tentukan content type berdasarkan ekstensi
            String contentType = "image/jpeg";
            String fn = filename.toLowerCase();
            if (fn.endsWith(".png")) contentType = "image/png";
            else if (fn.endsWith(".gif")) contentType = "image/gif";
            else if (fn.endsWith(".webp")) contentType = "image/webp";
            else if (fn.endsWith(".svg")) contentType = "image/svg+xml";

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .body(resource);

        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
