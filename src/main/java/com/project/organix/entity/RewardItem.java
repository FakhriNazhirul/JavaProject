package com.project.organix.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RewardItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String namaItem;
    private Integer hargaPoin;
    // --- GENERATE GETTER & SETTER ---

    public Integer getHargaPoin() {
        return hargaPoin;
    }

    public void setHargaPoin(Integer hargaPoin) {
        this.hargaPoin = hargaPoin;
    }

    public String getNamaItem() {
        return namaItem;
    }

    public void setNamaItem(String namaItem) {
        this.namaItem = namaItem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}