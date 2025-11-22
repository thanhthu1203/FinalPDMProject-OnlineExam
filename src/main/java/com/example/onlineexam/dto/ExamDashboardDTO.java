
package com.example.onlineexam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExamDashboardDTO {

    private String examId;
    private String courseName;
    private String eType;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime examDate;

    private String room;
    private Integer score;

    // Constructor - MUST match the order in your service
    public ExamDashboardDTO(String examId, String courseName, LocalDateTime examDate,
                            String room, Integer score, String eType) {
        this.examId = examId;
        this.courseName = courseName;
        this.examDate = examDate;
        this.room = room;
        this.score = score;
        this.eType = eType;
    }

    // Default constructor (required for Jackson)
    public ExamDashboardDTO() {
    }
    @Override
    public String toString() {
        return "ExamDashboardDTO{" +
                "examId='" + examId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", eType='" + eType + '\'' +
                ", examDate=" + examDate +
                ", room='" + room + '\'' +
                ", score=" + score +
                '}';
    }
}
