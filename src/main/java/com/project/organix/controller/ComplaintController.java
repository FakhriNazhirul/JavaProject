package com.project.organix.controller;

import com.project.organix.model.Complaint;
import com.project.organix.service.interfacee.ComplaintService;
import com.project.organix.dto.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin(origins = "*")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Complaint>>> getAllComplaints() {
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Complaint>>> getUserComplaints(@PathVariable Long userId) {
        return ResponseEntity.ok(complaintService.getUserComplaints(userId));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Complaint>> createComplaint(@RequestBody Complaint complaint) {
        return ResponseEntity.ok(complaintService.createComplaint(complaint));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Complaint>> updateComplaint(
            @PathVariable Long id, @RequestBody Complaint complaint) {
        return ResponseEntity.ok(complaintService.updateComplaint(id, complaint));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteComplaint(@PathVariable Long id) {
        return ResponseEntity.ok(complaintService.deleteComplaint(id));
    }
}