package com.project.organix.service.impl;

import com.project.organix.model.RewardItem;
import com.project.organix.model.User;
import com.project.organix.model.PointHasil;
import com.project.organix.repository.RewardItemRepository;
import com.project.organix.repository.UserRepository;
import com.project.organix.repository.PointHasilRepository;
import com.project.organix.service.interfacee.RewardService;
import com.project.organix.dto.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class RewardServiceImpl implements RewardService {

    @Autowired
    private RewardItemRepository rewardItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PointHasilRepository pointHasilRepository;

    @Override
    public ApiResponse<List<RewardItem>> getAllRewards() {
        return ApiResponse.ok("Rewards retrieved successfully", rewardItemRepository.findAll());
    }

    @Override
    public ApiResponse<List<RewardItem>> getAvailableRewards() {
        return ApiResponse.ok("Available rewards retrieved", rewardItemRepository.findByStockGreaterThan(0));
    }

    @Override
    public ApiResponse<RewardItem> getRewardById(Long id) {
        return rewardItemRepository.findById(id)
                .map(reward -> ApiResponse.ok("Reward found", reward))
                .orElse(ApiResponse.error("Reward not found", null));
    }

    @Override
    public ApiResponse<RewardItem> createReward(RewardItem reward) {
        return ApiResponse.ok("Reward created successfully", rewardItemRepository.save(reward));
    }

    @Override
    public ApiResponse<RewardItem> updateReward(Long id, RewardItem reward) {
        return rewardItemRepository.findById(id).map(existing -> {
            existing.setName(reward.getName());
            existing.setDescription(reward.getDescription());
            existing.setPriceInPoints(reward.getPriceInPoints());
            existing.setStock(reward.getStock());
            existing.setImageUrl(reward.getImageUrl());
            rewardItemRepository.save(existing);
            return ApiResponse.ok("Reward updated successfully", existing);
        }).orElse(ApiResponse.error("Reward not found", null));
    }

    @Override
    public ApiResponse<String> deleteReward(Long id) {
        if (rewardItemRepository.existsById(id)) {
            rewardItemRepository.deleteById(id);
            return ApiResponse.ok("Reward deleted successfully", "Success");
        }
        return ApiResponse.error("Reward not found", null);
    }

    @Override
    @Transactional
    public ApiResponse<String> redeemReward(Long userId, Long rewardId, int quantity) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<RewardItem> rewardOpt = rewardItemRepository.findById(rewardId);

        if (userOpt.isEmpty()) {
            return ApiResponse.error("User not found", null);
        }
        if (rewardOpt.isEmpty()) {
            return ApiResponse.error("Reward not found", null);
        }

        User user = userOpt.get();
        RewardItem reward = rewardOpt.get();

        BigDecimal totalPoints = reward.getPriceInPoints().multiply(BigDecimal.valueOf(quantity));

        if (user.getPoints() < totalPoints.intValue()) {
            return ApiResponse.error("Insufficient points", null);
        }

        if (reward.getStock() < quantity) {
            return ApiResponse.error("Insufficient stock", null);
        }

        user.setPoints(user.getPoints() - totalPoints.intValue());
        userRepository.save(user);

        reward.setStock(reward.getStock() - quantity);
        rewardItemRepository.save(reward);

        PointHasil deduction = new PointHasil();
        deduction.setUserId(userId);
        deduction.setPoints(totalPoints.negate());
        deduction.setType("REDEEM");
        deduction.setDescription("Redeem reward: " + reward.getName() + " x" + quantity);
        pointHasilRepository.save(deduction);

        return ApiResponse.ok("Reward redeemed successfully", "Success");
    }
}