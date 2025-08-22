package com.laitusneo.backend.repository;

import com.laitusneo.backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartmentId(Long departmentId);
    List<Employee> findByRoleId(Long roleId);
    List<Employee> findByManagerId(Long managerId);
}
