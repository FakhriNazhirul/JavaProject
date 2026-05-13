package com.project.organix.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "point_hasil")
public class PointHasil extends BaseEntity {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal points;

    @Column(nullable = false, length = 50)
    private String type;

    @Column(length = 1000)
    private String description;
}