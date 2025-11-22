package com.example.onlineexam.controller;

import com.example.onlineexam.dto.ExamDashboardDTO;
import com.example.onlineexam.dto.LoginRequest;
import com.example.onlineexam.dto.LoginResponse;
import com.example.onlineexam.entity.Student;
import com.example.onlineexam.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.onlineexam.dto.ExamDashboardDTO;
import com.example.onlineexam.dto.LoginRequest;
import com.example.onlineexam.dto.LoginResponse;
import com.example.onlineexam.entity.Student;
import com.example.onlineexam.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allow all origins for development
public class StudentController {

    private static final Logger log = LoggerFactory.getLogger(StudentController.class);
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            log.info("Login attempt for student ID: {}", request.getStudentId());

            if (request.getStudentId() == null || request.getPassword() == null) {
                log.warn("Missing credentials in login request");
                return ResponseEntity.badRequest()
                        .body(new LoginResponse(false, "Student ID and password are required", null));
            }

            Student s = studentService.findById(request.getStudentId());

            if (s == null) {
                log.warn("Student not found: {}", request.getStudentId());
                return ResponseEntity.ok(new LoginResponse(false, "Student not found", null));
            }

            // Password validation with null check
            if (s.getPassword() != null && s.getPassword().equals(request.getPassword())) {
                log.info("Login successful for student: {}", s.getStudentId());
                return ResponseEntity.ok(new LoginResponse(true, "Login successful", s.getStudentId()));
            } else {
                log.warn("Invalid password for student: {}", request.getStudentId());
                return ResponseEntity.ok(new LoginResponse(false, "Invalid credentials", null));
            }
        } catch (Exception e) {
            log.error("Error during login", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponse(false, "Server error occurred", null));
        }
    }

    @GetMapping("/student/{id}/dashboard")
    public ResponseEntity<?> getDashboard(@PathVariable("id") String id) {
        try {
            log.info("Fetching dashboard for student: {}", id);

            if (id == null || id.trim().isEmpty()) {
                log.warn("Invalid student ID provided");
                return ResponseEntity.badRequest().body("Invalid student ID");
            }

            List<ExamDashboardDTO> dto = studentService.getDashboardForStudent(id);

            if (dto == null || dto.isEmpty()) {
                log.info("No exams found for student: {}", id);
                return ResponseEntity.ok(List.of()); // Return empty list instead of null
            }

            log.info("Retrieved {} exams for student: {}", dto.size(), id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            log.error("Error fetching dashboard for student: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching dashboard data");
        }
    }
}