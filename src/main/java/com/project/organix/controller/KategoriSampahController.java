package com.project.organix.controller;

import com.project.organix.entity.KategoriSampah;
import com.project.organix.repository.KategoriSampahRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class KategoriSampahController {

    @Autowired
    private KategoriSampahRepository kategoriSampahRepository;

    // 4. Melihat daftar kategori sampah dan harga poin [cite: 120, 129]
    @GetMapping
    public List<KategoriSampah> getAllCategories() {
        return kategoriSampahRepository.findAll();
    }
}