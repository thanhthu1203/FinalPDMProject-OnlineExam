package com.example.onlineexam.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter //dont worry these things make code more clean that all
@Table(name = "Result")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Result_ID")
    private Long resultId;

    @ManyToOne
    @JoinColumn(name = "Student_ID")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "Exam_ID")
    private Exam exam;

    @Column(name = "Score")
    private Integer score;

    // Getters and setters
    /*
    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }*/
}