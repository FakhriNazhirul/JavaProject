package com.project.organix.service.interfacee;

import com.project.organix.dto.response.ApiResponse;
import com.project.organix.model.RewardItem;

import java.util.List;

public interface RewardService {
    ApiResponse<List<RewardItem>> getAllRewards();
    ApiResponse<List<RewardItem>> getAvailableRewards();
    ApiResponse<RewardItem> getRewardById(Long id);
    ApiResponse<RewardItem> createReward(RewardItem reward);
    ApiResponse<RewardItem> updateReward(Long id, RewardItem reward);
    ApiResponse<String> deleteReward(Long id);
    ApiResponse<String> redeemReward(Long userId, Long rewardId, int quantity);
}