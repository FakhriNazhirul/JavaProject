package com.project.organix.service.impl;

import com.project.organix.model.Transaksi;
import com.project.organix.repository.TransaksiRepository;
import com.project.organix.service.interfacee.WasteService;
import com.project.organix.dto.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WasteServiceImpl implements WasteService {

    @Autowired
    private TransaksiRepository transaksiRepository;

    @Override
    public ApiResponse<List<Transaksi>> getAllTransactions() {
        List<Transaksi> transactions = transaksiRepository.findAll();
        return ApiResponse.ok("Transactions retrieved successfully", transactions);
    }

    @Override
    public ApiResponse<Transaksi> getTransactionById(Long id) {
        Optional<Transaksi> transaction = transaksiRepository.findById(id);
        if (transaction.isPresent()) {
            return ApiResponse.ok("Transaction found", transaction.get());
        }
        return ApiResponse.error("Transaction not found with id: " + id, null);
    }

    @Override
    public ApiResponse<List<Transaksi>> getTransactionsByUserId(Long userId) {
        List<Transaksi> transactions = transaksiRepository.findByUserId(userId);
        return ApiResponse.ok("Transactions retrieved successfully", transactions);
    }

    @Override
    public ApiResponse<Transaksi> createTransaction(Transaksi transaction) {
        Transaksi saved = transaksiRepository.save(transaction);
        return ApiResponse.ok("Transaction created successfully", saved);
    }

    @Override
    public ApiResponse<Transaksi> updateTransaction(Long id, Transaksi transaction) {
        Optional<Transaksi> existing = transaksiRepository.findById(id);
        if (existing.isPresent()) {
            Transaksi updated = existing.get();
            updated.setUserId(transaction.getUserId());
            updated.setKategoriSampahId(transaction.getKategoriSampahId());
            updated.setWeight(transaction.getWeight());
            updated.setTotalPoints(transaction.getTotalPoints());
            updated.setStatus(transaction.getStatus());
            transaksiRepository.save(updated);
            return ApiResponse.ok("Transaction updated successfully", updated);
        }
        return ApiResponse.error("Transaction not found with id: " + id, null);
    }

    @Override
    public ApiResponse<String> deleteTransaction(Long id) {
        if (transaksiRepository.existsById(id)) {
            transaksiRepository.deleteById(id);
            return ApiResponse.ok("Transaction deleted successfully", "Success");
        }
        return ApiResponse.error("Transaction not found with id: " + id, null);
    }
}