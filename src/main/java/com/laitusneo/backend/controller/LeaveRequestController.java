package com.laitusneo.backend.controller;

import com.laitusneo.backend.entity.LeaveRequest;
import com.laitusneo.backend.service.LeaveRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-requests")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    // Apply for Leave
    @PostMapping
    public ResponseEntity<LeaveRequest> applyLeave(@RequestBody LeaveRequest leaveRequest) {
        return ResponseEntity.ok(leaveRequestService.applyLeave(leaveRequest));
    }

    // Get All Leave Requests
    @GetMapping
    public ResponseEntity<List<LeaveRequest>> getAllLeaveRequests() {
        return ResponseEntity.ok(leaveRequestService.getAllLeaveRequests());
    }

    // Get Leave Requests by Employee
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<LeaveRequest>> getLeaveRequestsByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(leaveRequestService.getLeaveRequestsByEmployee(employeeId));
    }

    // Approve Leave
    @PutMapping("/{id}/approve/{approverId}")
    public ResponseEntity<LeaveRequest> approveLeave(@PathVariable Long id, @PathVariable Long approverId) {
        return ResponseEntity.ok(leaveRequestService.approveLeave(id, approverId));
    }

    // Reject Leave
    @PutMapping("/{id}/reject/{approverId}")
    public ResponseEntity<LeaveRequest> rejectLeave(@PathVariable Long id, @PathVariable Long approverId) {
        return ResponseEntity.ok(leaveRequestService.rejectLeave(id, approverId));
    }

    // Delete Leave Request
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeaveRequest(@PathVariable Long id) {
        leaveRequestService.deleteLeaveRequest(id);
        return ResponseEntity.noContent().build();
    }
}
