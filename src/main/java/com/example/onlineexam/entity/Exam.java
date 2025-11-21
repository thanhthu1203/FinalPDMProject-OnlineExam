package com.example.onlineexam.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Exam")
public class Exam {

    @Id
    @Column(name = "Exam_ID", length = 7)
    private String examId;

    @Column(name = "EType", length = 1)
    private String eType;

    @Column(name = "RoomNumber")
    private String roomNumber;

    @Column(name = "TestDateDATETIME")
    private LocalDateTime testDateDATETIME;

    @Column(name = "Duration")
    private String duration;

    @ManyToOne
    @JoinColumn(name = "Course_Course_ID")
    private Course course;

    @ManyToMany(mappedBy = "exams")
    private Set<Student> students = new HashSet<>();

    // Getters and setters

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String geteType() {
        return eType;
    }

    public void seteType(String eType) {
        this.eType = eType;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public LocalDateTime getTestDateDATETIME() {
        return testDateDATETIME;
    }

    public void setTestDateDATETIME(LocalDateTime testDateDATETIME) {
        this.testDateDATETIME = testDateDATETIME;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}