package com.laitusneo.backend.repository;

import com.laitusneo.backend.entity.LeaveRequest;
import com.laitusneo.backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByEmployee(Employee employee);
    List<LeaveRequest> findByStatus(LeaveRequest.Status status);
}
