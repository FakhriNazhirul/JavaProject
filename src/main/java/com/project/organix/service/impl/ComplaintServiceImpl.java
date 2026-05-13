package com.project.organix.service.impl;

import com.project.organix.model.Complaint;
import com.project.organix.repository.ComplaintRepository;
import com.project.organix.service.interfacee.ComplaintService;
import com.project.organix.dto.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Override
    public ApiResponse<List<Complaint>> getAllComplaints() {
        List<Complaint> complaints = complaintRepository.findAll();
        return ApiResponse.ok("Complaints retrieved successfully", complaints);
    }

    @Override
    public ApiResponse<List<Complaint>> getUserComplaints(Long userId) {
        List<Complaint> complaints = complaintRepository.findByUserId(userId);
        return ApiResponse.ok("Complaints retrieved successfully", complaints);
    }

    @Override
    public ApiResponse<Complaint> createComplaint(Complaint complaint) {
        Complaint saved = complaintRepository.save(complaint);
        return ApiResponse.ok("Complaint created successfully", saved);
    }

    @Override
    public ApiResponse<Complaint> updateComplaint(Long id, Complaint complaint) {
        Optional<Complaint> existing = complaintRepository.findById(id);
        if (existing.isPresent()) {
            Complaint updated = existing.get();
            updated.setUserId(complaint.getUserId());
            updated.setSubject(complaint.getSubject());
            updated.setDescription(complaint.getDescription());
            updated.setStatus(complaint.getStatus());
            complaintRepository.save(updated);
            return ApiResponse.ok("Complaint updated successfully", updated);
        }
        return ApiResponse.error("Complaint not found with id: " + id, null);
    }

    @Override
    public ApiResponse<String> deleteComplaint(Long id) {
        if (complaintRepository.existsById(id)) {
            complaintRepository.deleteById(id);
            return ApiResponse.ok("Complaint deleted successfully", "Success");
        }
        return ApiResponse.error("Complaint not found with id: " + id, null);
    }
}