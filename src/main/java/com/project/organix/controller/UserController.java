package com.project.organix.controller;

import com.project.organix.model.User;
import com.project.organix.model.PointHasil;
import com.project.organix.repository.UserRepository;
import com.project.organix.service.interfacee.PointService;
import com.project.organix.dto.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PointService pointService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.ok("Users retrieved successfully", userRepository.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(ApiResponse.ok("User found", user)))
                .orElse(ResponseEntity.ok(ApiResponse.error("User not found with id: " + id, null)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody User user) {
        return ResponseEntity.ok(ApiResponse.ok("User created successfully", userRepository.save(user)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long id, @RequestBody User user) {
        return userRepository.findById(id).map(existing -> {
            existing.setName(user.getName());
            existing.setEmail(user.getEmail());
            existing.setPassword(user.getPassword());
            userRepository.save(existing);
            return ResponseEntity.ok(ApiResponse.ok("User updated successfully", existing));
        }).orElse(ResponseEntity.ok(ApiResponse.error("User not found with id: " + id, null)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok(ApiResponse.ok("User deleted successfully", "Success"));
        }
        return ResponseEntity.ok(ApiResponse.error("User not found with id: " + id, null));
    }

    @GetMapping("/{userId}/points")
    public ResponseEntity<ApiResponse<List<PointHasil>>> getUserPoints(@PathVariable Long userId) {
        return ResponseEntity.ok(pointService.getUserPoints(userId));
    }
}