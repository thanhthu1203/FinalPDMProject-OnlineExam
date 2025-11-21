package com.example.onlineexam.controller;

import com.example.onlineexam.dto.ExamDashboardDTO;
import com.example.onlineexam.dto.LoginRequest;
import com.example.onlineexam.dto.LoginResponse;
import com.example.onlineexam.entity.Student;
import com.example.onlineexam.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        Student s = studentService.findById(request.getStudentId());
        if (s == null) {
            return ResponseEntity.ok(new LoginResponse(false, "Student not found", null));
        }
        // NOTE: Passwords are compared as stored. In production use hashing + salt
        if (s.getPassword() != null && s.getPassword().equals(request.getPassword())) {
            return ResponseEntity.ok(new LoginResponse(true, "Login successful", s.getStudentId()));
        } else {
            return ResponseEntity.ok(new LoginResponse(false, "Invalid credentials", null));
        }
    }

    @GetMapping("/student/{id}/dashboard")
    public ResponseEntity<List<ExamDashboardDTO>> getDashboard(@PathVariable("id") String id) {
        List<ExamDashboardDTO> dto = studentService.getDashboardForStudent(id);
        return ResponseEntity.ok(dto);
    }
}