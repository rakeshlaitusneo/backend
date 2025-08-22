package com.laitusneo.backend.repository;

import com.laitusneo.backend.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    
    // Find attendance by employee ID and date
    Optional<Attendance> findByEmployeeIdAndDate(Long employeeId, LocalDate date);
    
    // Find all attendance records for an employee
    List<Attendance> findByEmployeeIdOrderByDateDesc(Long employeeId);
    
    // Find attendance records for employee within date range
    List<Attendance> findByEmployeeIdAndDateBetween(Long employeeId, LocalDate startDate, LocalDate endDate);
    
    // Find all attendance records for a specific date
    List<Attendance> findByDate(LocalDate date);
    
    // Find attendance records by status
    List<Attendance> findByStatus(Attendance.AttendanceStatus status);
    
    // Custom query to get monthly attendance summary
    @Query("SELECT a FROM Attendance a WHERE a.employeeId = :employeeId " +
           "AND YEAR(a.date) = :year AND MONTH(a.date) = :month ORDER BY a.date")
    List<Attendance> findMonthlyAttendance(@Param("employeeId") Long employeeId, 
                                         @Param("year") int year, 
                                         @Param("month") int month);
    
    // Check if attendance exists for employee on specific date
    boolean existsByEmployeeIdAndDate(Long employeeId, LocalDate date);
}