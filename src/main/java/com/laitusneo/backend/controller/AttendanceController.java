package com.laitusneo.backend.controller;

import com.laitusneo.backend.entity.Attendance;
import com.laitusneo.backend.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {
    
    @Autowired
    private AttendanceService attendanceService;
    
    // Punch In
    @PostMapping("/punch-in/{employeeId}")
    public ResponseEntity<?> punchIn(@PathVariable Long employeeId) {
        try {
            Attendance attendance = attendanceService.punchIn(employeeId);
            return ResponseEntity.ok(attendance);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    // Punch Out
    @PostMapping("/punch-out/{employeeId}")
    public ResponseEntity<?> punchOut(@PathVariable Long employeeId) {
        try {
            Attendance attendance = attendanceService.punchOut(employeeId);
            return ResponseEntity.ok(attendance);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    // Mark Leave
    @PostMapping("/mark-leave")
    public ResponseEntity<?> markLeave(@RequestParam Long employeeId, @RequestParam String date) {
        try {
            LocalDate leaveDate = LocalDate.parse(date);
            Attendance attendance = attendanceService.markLeave(employeeId, leaveDate);
            return ResponseEntity.ok(attendance);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    // Get attendance by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getAttendanceById(@PathVariable Long id) {
        Optional<Attendance> attendance = attendanceService.getAttendanceById(id);
        if (attendance.isPresent()) {
            return ResponseEntity.ok(attendance.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Get employee attendance for specific date
    @GetMapping("/employee/{employeeId}/date/{date}")
    public ResponseEntity<?> getAttendanceByEmployeeAndDate(
            @PathVariable Long employeeId, 
            @PathVariable String date) {
        try {
            LocalDate attendanceDate = LocalDate.parse(date);
            Optional<Attendance> attendance = attendanceService.getAttendanceByEmployeeAndDate(employeeId, attendanceDate);
            if (attendance.isPresent()) {
                return ResponseEntity.ok(attendance.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    // Get all attendance records for an employee
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Attendance>> getEmployeeAttendance(@PathVariable Long employeeId) {
        List<Attendance> attendanceList = attendanceService.getEmployeeAttendance(employeeId);
        return ResponseEntity.ok(attendanceList);
    }
    
    // Get attendance records for employee within date range
    @GetMapping("/employee/{employeeId}/range")
    public ResponseEntity<List<Attendance>> getAttendanceByDateRange(
            @PathVariable Long employeeId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            List<Attendance> attendanceList = attendanceService.getAttendanceByDateRange(employeeId, start, end);
            return ResponseEntity.ok(attendanceList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Get monthly attendance
    @GetMapping("/employee/{employeeId}/monthly")
    public ResponseEntity<List<Attendance>> getMonthlyAttendance(
            @PathVariable Long employeeId,
            @RequestParam int year,
            @RequestParam int month) {
        List<Attendance> attendanceList = attendanceService.getMonthlyAttendance(employeeId, year, month);
        return ResponseEntity.ok(attendanceList);
    }
    
    // Get daily attendance for all employees
    @GetMapping("/daily/{date}")
    public ResponseEntity<List<Attendance>> getDailyAttendance(@PathVariable String date) {
        try {
            LocalDate attendanceDate = LocalDate.parse(date);
            List<Attendance> attendanceList = attendanceService.getDailyAttendance(attendanceDate);
            return ResponseEntity.ok(attendanceList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Get all attendance records
    @GetMapping("/all")
    public ResponseEntity<List<Attendance>> getAllAttendance() {
        List<Attendance> attendanceList = attendanceService.getAllAttendance();
        return ResponseEntity.ok(attendanceList);
    }
    
    // Update attendance
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAttendance(@PathVariable Long id, @RequestBody Attendance attendance) {
        try {
            Attendance updatedAttendance = attendanceService.updateAttendance(id, attendance);
            return ResponseEntity.ok(updatedAttendance);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    // Delete attendance
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAttendance(@PathVariable Long id) {
        try {
            attendanceService.deleteAttendance(id);
            return ResponseEntity.ok("Attendance record deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}