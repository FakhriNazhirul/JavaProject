package com.project.organix.controller;

import com.project.organix.model.Transaksi;
import com.project.organix.service.interfacee.WasteService;
import com.project.organix.dto.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class WasteTransactionController {

    @Autowired
    private WasteService wasteService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Transaksi>>> getAllTransactions() {
        return ResponseEntity.ok(wasteService.getAllTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Transaksi>> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(wasteService.getTransactionById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Transaksi>>> getTransactionsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(wasteService.getTransactionsByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Transaksi>> createTransaction(@RequestBody Transaksi transaction) {
        return ResponseEntity.ok(wasteService.createTransaction(transaction));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Transaksi>> updateTransaction(@PathVariable Long id, @RequestBody Transaksi transaction) {
        return ResponseEntity.ok(wasteService.updateTransaction(id, transaction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTransaction(@PathVariable Long id) {
        return ResponseEntity.ok(wasteService.deleteTransaction(id));
    }
}