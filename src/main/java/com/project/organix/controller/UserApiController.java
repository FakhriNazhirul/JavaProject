package com.project.organix.controller;

import com.project.organix.dto.request.ComplaintRequest;
import com.project.organix.dto.request.UserProfileRequest;
import com.project.organix.dto.response.ApiResponse;
import com.project.organix.model.Complaint;
import com.project.organix.model.PointHasil;
import com.project.organix.model.User;
import com.project.organix.repository.UserRepository;
import com.project.organix.service.interfacee.ComplaintService;
import com.project.organix.service.interfacee.PointService;
import com.project.organix.service.interfacee.RewardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserApiController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PointService pointService;

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private RewardService rewardService;

    @GetMapping("/{userId}/points")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getUserPoints(@PathVariable Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    List<PointHasil> pointHistory = pointService.getUserPoints(userId).getData();
                    int totalPoints = user.getPoints();
                    return ResponseEntity.ok(ApiResponse.ok("Points retrieved successfully",
                            Map.of(
                                    "totalPoints", totalPoints,
                                    "pointHistory", pointHistory
                            )));
                })
                .orElse(ResponseEntity.ok(ApiResponse.error("User not found", null)));
    }

    @PostMapping("/{userId}/points/redeem")
    public ResponseEntity<ApiResponse<String>> redeemPoints(
            @PathVariable Long userId,
            @RequestParam Long points) {
        return ResponseEntity.ok(pointService.redeemPoints(userId, points));
    }

    @PostMapping("/{userId}/rewards/redeem")
    public ResponseEntity<ApiResponse<String>> redeemReward(
            @PathVariable Long userId,
            @RequestParam Long rewardId,
            @RequestParam(defaultValue = "1") int quantity) {
        return ResponseEntity.ok(rewardService.redeemReward(userId, rewardId, quantity));
    }

    @GetMapping("/{userId}/complaints")
    public ResponseEntity<ApiResponse<List<Complaint>>> getUserComplaints(@PathVariable Long userId) {
        return ResponseEntity.ok(complaintService.getUserComplaints(userId));
    }

    @PostMapping("/{userId}/complaints")
    public ResponseEntity<ApiResponse<Complaint>> createComplaint(
            @PathVariable Long userId,
            @Valid @RequestBody ComplaintRequest request) {
        Complaint complaint = new Complaint();
        complaint.setUserId(userId);
        complaint.setSubject(request.getSubject());
        complaint.setDescription(request.getDescription());
        return ResponseEntity.ok(complaintService.createComplaint(complaint));
    }

    @GetMapping("/{userId}/profile")
    public ResponseEntity<ApiResponse<User>> getUserProfile(@PathVariable Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setPassword(null);
                    return ResponseEntity.ok(ApiResponse.ok("Profile retrieved successfully", user));
                })
                .orElse(ResponseEntity.ok(ApiResponse.error("User not found", null)));
    }

    @PutMapping("/{userId}/profile")
    public ResponseEntity<ApiResponse<User>> updateUserProfile(
            @PathVariable Long userId,
            @Valid @RequestBody UserProfileRequest request) {
        return userRepository.findById(userId)
                .map(existing -> {
                    existing.setName(request.getName());
                    existing.setEmail(request.getEmail());
                    if (request.getPassword() != null && !request.getPassword().isEmpty()) {
                        existing.setPassword(request.getPassword());
                    }
                    User saved = userRepository.save(existing);
                    saved.setPassword(null);
                    return ResponseEntity.ok(ApiResponse.ok("Profile updated successfully", saved));
                })
                .orElse(ResponseEntity.ok(ApiResponse.error("User not found", null)));
    }
}
