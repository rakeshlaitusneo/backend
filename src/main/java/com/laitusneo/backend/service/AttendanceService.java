package com.laitusneo.backend.service;

import com.laitusneo.backend.entity.Attendance;
import com.laitusneo.backend.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {
    
    @Autowired
    private AttendanceRepository attendanceRepository;
    
    // Punch In
    public Attendance punchIn(Long employeeId) {
        LocalDate today = LocalDate.now();
        
        // Check if already punched in today
        if (attendanceRepository.existsByEmployeeIdAndDate(employeeId, today)) {
            throw new RuntimeException("Employee has already punched in today");
        }
        
        Attendance attendance = new Attendance();
        attendance.setEmployeeId(employeeId);
        attendance.setDate(today);
        attendance.setPunchInTime(LocalDateTime.now());
        attendance.setStatus(Attendance.AttendanceStatus.PRESENT);
        
        return attendanceRepository.save(attendance);
    }
    
    // Punch Out
    public Attendance punchOut(Long employeeId) {
        LocalDate today = LocalDate.now();
        
        Optional<Attendance> attendanceOpt = attendanceRepository.findByEmployeeIdAndDate(employeeId, today);
        
        if (attendanceOpt.isEmpty()) {
            throw new RuntimeException("No punch-in record found for today");
        }
        
        Attendance attendance = attendanceOpt.get();
        
        if (attendance.getPunchOutTime() != null) {
            throw new RuntimeException("Employee has already punched out today");
        }
        
        LocalDateTime punchOutTime = LocalDateTime.now();
        attendance.setPunchOutTime(punchOutTime);
        
        // Calculate total hours
        Duration duration = Duration.between(attendance.getPunchInTime(), punchOutTime);
        double hours = duration.toMinutes() / 60.0;
        attendance.setTotalHours(BigDecimal.valueOf(hours).setScale(2, RoundingMode.HALF_UP));
        
        return attendanceRepository.save(attendance);
    }
    
    // Mark Leave
    public Attendance markLeave(Long employeeId, LocalDate date) {
        if (attendanceRepository.existsByEmployeeIdAndDate(employeeId, date)) {
            throw new RuntimeException("Attendance record already exists for this date");
        }
        
        Attendance attendance = new Attendance();
        attendance.setEmployeeId(employeeId);
        attendance.setDate(date);
        attendance.setStatus(Attendance.AttendanceStatus.LEAVE);
        
        return attendanceRepository.save(attendance);
    }
    
    // Get attendance by ID
    public Optional<Attendance> getAttendanceById(Long id) {
        return attendanceRepository.findById(id);
    }
    
    // Get employee attendance for specific date
    public Optional<Attendance> getAttendanceByEmployeeAndDate(Long employeeId, LocalDate date) {
        return attendanceRepository.findByEmployeeIdAndDate(employeeId, date);
    }
    
    // Get all attendance records for an employee
    public List<Attendance> getEmployeeAttendance(Long employeeId) {
        return attendanceRepository.findByEmployeeIdOrderByDateDesc(employeeId);
    }
    
    // Get attendance records for employee within date range
    public List<Attendance> getAttendanceByDateRange(Long employeeId, LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByEmployeeIdAndDateBetween(employeeId, startDate, endDate);
    }
    
    // Get monthly attendance
    public List<Attendance> getMonthlyAttendance(Long employeeId, int year, int month) {
        return attendanceRepository.findMonthlyAttendance(employeeId, year, month);
    }
    
    // Get all attendance records for a specific date
    public List<Attendance> getDailyAttendance(LocalDate date) {
        return attendanceRepository.findByDate(date);
    }
    
    // Get all attendance records
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }
    
    // Update attendance
    public Attendance updateAttendance(Long id, Attendance updatedAttendance) {
        Optional<Attendance> existingOpt = attendanceRepository.findById(id);
        
        if (existingOpt.isEmpty()) {
            throw new RuntimeException("Attendance record not found");
        }
        
        Attendance existing = existingOpt.get();
        
        // Update fields
        if (updatedAttendance.getPunchInTime() != null) {
            existing.setPunchInTime(updatedAttendance.getPunchInTime());
        }
        if (updatedAttendance.getPunchOutTime() != null) {
            existing.setPunchOutTime(updatedAttendance.getPunchOutTime());
        }
        if (updatedAttendance.getTotalHours() != null) {
            existing.setTotalHours(updatedAttendance.getTotalHours());
        }
        if (updatedAttendance.getStatus() != null) {
            existing.setStatus(updatedAttendance.getStatus());
        }
        
        // Recalculate total hours if both punch times are available
        if (existing.getPunchInTime() != null && existing.getPunchOutTime() != null) {
            Duration duration = Duration.between(existing.getPunchInTime(), existing.getPunchOutTime());
            double hours = duration.toMinutes() / 60.0;
            existing.setTotalHours(BigDecimal.valueOf(hours).setScale(2, RoundingMode.HALF_UP));
        }
        
        return attendanceRepository.save(existing);
    }
    
    // Delete attendance
    public void deleteAttendance(Long id) {
        if (!attendanceRepository.existsById(id)) {
            throw new RuntimeException("Attendance record not found");
        }
        attendanceRepository.deleteById(id);
    }
}