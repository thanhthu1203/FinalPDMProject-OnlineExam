package com.example.onlineexam.dto;

public class LoginRequest {
    private String studentId;
    private String password;

    public LoginRequest() {}

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}