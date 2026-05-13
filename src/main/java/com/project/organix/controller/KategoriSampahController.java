package com.project.organix.controller;

import com.project.organix.model.KategoriSampah;
import com.project.organix.repository.KategoriSampahRepository;
import com.project.organix.dto.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kategori")
@CrossOrigin(origins = "*")
public class KategoriSampahController {

    @Autowired
    private KategoriSampahRepository kategoriSampahRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<KategoriSampah>>> getAllKategori() {
        return ResponseEntity.ok(ApiResponse.ok("Categories retrieved", kategoriSampahRepository.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<KategoriSampah>> getKategoriById(@PathVariable Long id) {
        return kategoriSampahRepository.findById(id)
                .map(k -> ResponseEntity.ok(ApiResponse.ok("Found", k)))
                .orElse(ResponseEntity.ok(ApiResponse.error("Not found", null)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<KategoriSampah>> createKategori(@RequestBody KategoriSampah kategori) {
        return ResponseEntity.ok(ApiResponse.ok("Created", kategoriSampahRepository.save(kategori)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<KategoriSampah>> updateKategori(@PathVariable Long id, @RequestBody KategoriSampah kategori) {
        return kategoriSampahRepository.findById(id).map(existing -> {
            existing.setName(kategori.getName());
            existing.setDescription(kategori.getDescription());
            kategoriSampahRepository.save(existing);
            return ResponseEntity.ok(ApiResponse.ok("Updated", existing));
        }).orElse(ResponseEntity.ok(ApiResponse.error("Not found", null)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteKategori(@PathVariable Long id) {
        if (kategoriSampahRepository.existsById(id)) {
            kategoriSampahRepository.deleteById(id);
            return ResponseEntity.ok(ApiResponse.ok("Deleted", "Success"));
        }
        return ResponseEntity.ok(ApiResponse.error("Not found", null));
    }
}