package com.project.organix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.organix.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Fungsi bawaan: save(), findAll(), findById(), deleteById()
}