package com.project.organix.service.interfacee;

import com.project.organix.dto.response.ApiResponse;
import com.project.organix.model.Transaksi;

import java.util.List;

public interface WasteService {
    ApiResponse<List<Transaksi>> getAllTransactions();
    ApiResponse<Transaksi> getTransactionById(Long id);
    ApiResponse<List<Transaksi>> getTransactionsByUserId(Long userId);
    ApiResponse<Transaksi> createTransaction(Transaksi transaction);
    ApiResponse<Transaksi> updateTransaction(Long id, Transaksi transaction);
    ApiResponse<String> deleteTransaction(Long id);
}