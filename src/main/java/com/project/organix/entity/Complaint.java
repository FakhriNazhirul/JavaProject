package com.project.organix.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Complaint {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String isiLaporan;
    private String status;
    // --- GENERATE GETTER & SETTER ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsiLaporan() {
        return isiLaporan;
    }

    public void setIsiLaporan(String isiLaporan) {
        this.isiLaporan = isiLaporan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}