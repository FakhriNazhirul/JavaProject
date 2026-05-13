package com.project.organix.controller;

import com.project.organix.model.PointHasil;
import com.project.organix.service.interfacee.PointService;
import com.project.organix.dto.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/points")
@CrossOrigin(origins = "*")
public class PointController {

    @Autowired
    private PointService pointService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<PointHasil>>> getUserPoints(@PathVariable Long userId) {
        return ResponseEntity.ok(pointService.getUserPoints(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<PointHasil>> addPoints(@RequestBody PointHasil pointHasil) {
        return ResponseEntity.ok(pointService.addPoints(pointHasil));
    }

    @PostMapping("/redeem")
    public ResponseEntity<ApiResponse<String>> redeemPoints(
            @RequestParam Long userId,
            @RequestParam Long points) {
        return ResponseEntity.ok(pointService.redeemPoints(userId, points));
    }
}