package com.example.onlineexam.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "Student")
public class Student {

    @Id
    @Column(name = "Student_ID", length = 11)
    private String studentId;

    @Column(name = "SName")
    private String sName;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "Password")
    private String password;

    @ManyToMany
    @JoinTable(
            name = "Student_has_Exam",
            joinColumns = @JoinColumn(name = "Student_Student_ID"),
            inverseJoinColumns = @JoinColumn(name = "Exam_Exam_ID")
    )
    private Set<Exam> exams = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "student_has_course",
            joinColumns = @JoinColumn(name = "Student_Student_ID"),
            inverseJoinColumns = @JoinColumn(name = "Course_Course_ID")
    )
    private List<Course> courses;
    // Relationship: One Student -> Many Results
    @OneToMany(mappedBy = "student")
    private List<Result> results;
}

    // Getters and setters
   /*
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Exam> getExams() {
        return exams;
    }

    public void setExams(Set<Exam> exams) {
        this.exams = exams;
    }*/