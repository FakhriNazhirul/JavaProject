package com.project.organix.service.interfacee;

import com.project.organix.dto.response.ApiResponse;
import com.project.organix.model.Complaint;

import java.util.List;

public interface ComplaintService {
    ApiResponse<List<Complaint>> getAllComplaints();
    ApiResponse<List<Complaint>> getUserComplaints(Long userId);
    ApiResponse<Complaint> createComplaint(Complaint complaint);
    ApiResponse<Complaint> updateComplaint(Long id, Complaint complaint);
    ApiResponse<String> deleteComplaint(Long id);
}