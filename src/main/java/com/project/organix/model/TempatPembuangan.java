package com.project.organix.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tempat_pembuangan")
public class TempatPembuangan extends BaseEntity {

    @Column(nullable = false)
    private String nama;

    @Column(length = 500)
    private String alamat;

    @Column(length = 100)
    private String kota;

    @Column(length = 50)
    private String status = "AKTIF";
}
