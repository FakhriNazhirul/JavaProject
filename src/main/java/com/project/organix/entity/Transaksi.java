package com.project.organix.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaksi")
public class Transaksi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // Satu user bisa banyak transaksi [cite: 113]
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime tanggal = LocalDateTime.now();
    private Double totalBerat;
    private Integer totalPoint;
    private String status = "SELESAI";

    // --- GENERATE GETTER & SETTER DISINI ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDateTime tanggal) {
        this.tanggal = tanggal;
    }

    public Double getTotalBerat() {
        return totalBerat;
    }

    public void setTotalBerat(Double totalBerat) {
        this.totalBerat = totalBerat;
    }

    public Integer getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}