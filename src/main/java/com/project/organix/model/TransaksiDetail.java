package com.project.organix.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaksi_detail")
public class TransaksiDetail extends BaseEntity {

    @Column(nullable = false)
    private Long transaksiId;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal weight;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal points;
}