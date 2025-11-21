package com.example.onlineexam.dto;

import java.time.LocalDateTime;

public class ExamDashboardDTO {
    private String examId;
    private String courseName;
    private LocalDateTime examDate;
    private String room;
    private Integer score;
    private String eType;

    public ExamDashboardDTO() {}

    public ExamDashboardDTO(String examId, String courseName, LocalDateTime examDate, String room, Integer score, String eType) {
        this.examId = examId;
        this.courseName = courseName;
        this.examDate = examDate;
        this.room = room;
        this.score = score;
        this.eType = eType;
    }

    // Getters and setters

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public LocalDateTime getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDateTime examDate) {
        this.examDate = examDate;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String geteType() {
        return eType;
    }

    public void seteType(String eType) {
        this.eType = eType;
    }
}