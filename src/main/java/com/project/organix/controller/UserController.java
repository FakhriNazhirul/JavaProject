package com.project.organix.controller;

import com.project.organix.entity.User;
import com.project.organix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // GET /api/users - Melihat semua warga 
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // GET /api/users/{id} - Melihat detail satu warga 
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // POST /api/users - Registrasi warga baru 
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }
}