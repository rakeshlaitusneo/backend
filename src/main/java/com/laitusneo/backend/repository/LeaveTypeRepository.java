package com.laitusneo.backend.repository;

import com.laitusneo.backend.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {
    Optional<LeaveType> findByTypeName(String typeName);
}
