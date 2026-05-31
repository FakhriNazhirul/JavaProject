package com.project.organix.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "complaints")
public class Complaint extends BaseEntity {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 200)
    private String subject;

    @Column(length = 100)
    private String category = "GENERAL";

    @Column(length = 50)
    private String priority = "MEDIUM";

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(length = 50)
    private String status = "OPEN";

    @Column(length = 2000)
    private String adminReply;
}
