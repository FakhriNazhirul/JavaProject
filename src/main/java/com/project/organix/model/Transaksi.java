package com.project.organix.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaksi")
public class Transaksi extends BaseEntity {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long kategoriSampahId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal weight;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPoints;

    @Column(length = 50)
    private String status = "PENDING";

    private Long tempatPembuanganId;
}