package com.example.onlineexam.dto;

public class LoginResponse {
    private boolean success;
    private String message;
    private String studentId;

    public LoginResponse() {}

    public LoginResponse(boolean success, String message, String studentId) {
        this.success = success;
        this.message = message;
        this.studentId = studentId;
    }

    // Getters and setters

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}