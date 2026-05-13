package com.project.organix.controller;

import com.project.organix.model.RewardItem;
import com.project.organix.service.impl.RewardServiceImpl;
import com.project.organix.dto.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rewards")
@CrossOrigin(origins = "*")
public class RewardController {

    @Autowired
    private RewardServiceImpl rewardService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<RewardItem>>> getAllRewards() {
        return ResponseEntity.ok(rewardService.getAllRewards());
    }

    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<RewardItem>>> getAvailableRewards() {
        return ResponseEntity.ok(rewardService.getAvailableRewards());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RewardItem>> getRewardById(@PathVariable Long id) {
        return ResponseEntity.ok(rewardService.getRewardById(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RewardItem>> createReward(@RequestBody RewardItem reward) {
        return ResponseEntity.ok(rewardService.createReward(reward));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RewardItem>> updateReward(@PathVariable Long id, @RequestBody RewardItem reward) {
        return ResponseEntity.ok(rewardService.updateReward(id, reward));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteReward(@PathVariable Long id) {
        return ResponseEntity.ok(rewardService.deleteReward(id));
    }

    @PostMapping("/redeem")
    public ResponseEntity<ApiResponse<String>> redeemReward(
            @RequestParam Long userId,
            @RequestParam Long rewardId,
            @RequestParam(defaultValue = "1") int quantity) {
        return ResponseEntity.ok(rewardService.redeemReward(userId, rewardId, quantity));
    }
}