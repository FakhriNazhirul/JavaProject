package com.project.organix.service.impl;

import com.project.organix.model.PointHasil;
import com.project.organix.model.User;
import com.project.organix.repository.PointHasilRepository;
import com.project.organix.repository.UserRepository;
import com.project.organix.service.interfacee.PointService;
import com.project.organix.dto.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PointServiceImpl implements PointService {

    @Autowired
    private PointHasilRepository pointHasilRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ApiResponse<List<PointHasil>> getUserPoints(Long userId) {
        List<PointHasil> points = pointHasilRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return ApiResponse.ok("Points retrieved successfully", points);
    }

    @Override
    @Transactional
    public ApiResponse<PointHasil> addPoints(PointHasil pointHasil) {
        PointHasil saved = pointHasilRepository.save(pointHasil);
        // Update user total points
        Optional<User> userOpt = userRepository.findById(pointHasil.getUserId());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPoints(user.getPoints() + pointHasil.getPoints().intValue());
            userRepository.save(user);
        }
        return ApiResponse.ok("Points added successfully", saved);
    }

    @Override
    @Transactional
    public ApiResponse<String> redeemPoints(Long userId, Long points) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPoints() >= points.intValue()) {
                user.setPoints(user.getPoints() - points.intValue());
                userRepository.save(user);

                PointHasil deduction = new PointHasil();
                deduction.setUserId(userId);
                deduction.setPoints(new java.math.BigDecimal(-points));
                deduction.setType("DEDUCT");
                deduction.setDescription("Redeem points");
                pointHasilRepository.save(deduction);

                return ApiResponse.ok("Points redeemed successfully", "Success");
            }
            return ApiResponse.error("Insufficient points", null);
        }
        return ApiResponse.error("User not found with id: " + userId, null);
    }
}