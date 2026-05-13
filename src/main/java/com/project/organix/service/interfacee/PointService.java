package com.project.organix.service.interfacee;

import com.project.organix.dto.response.ApiResponse;
import com.project.organix.model.PointHasil;

import java.util.List;

public interface PointService {
    ApiResponse<List<PointHasil>> getUserPoints(Long userId);
    ApiResponse<PointHasil> addPoints(PointHasil pointHasil);
    ApiResponse<String> redeemPoints(Long userId, Long points);
}