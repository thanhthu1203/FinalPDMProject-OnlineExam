package com.example.onlineexam.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "Course")
public class Course {

    @Id
    @Column(name = "Course_ID", length = 7)
    private String courseId;

    @Column(name = "CName")
    private String cName;

    @OneToMany(mappedBy = "course")
    private Set<Exam> exams = new HashSet<>();

    // Relationship: Many Courses <-> Many Students
    @ManyToMany(mappedBy = "courses")
    private List<Student> students;

    @ManyToOne
    @JoinColumn(name = "OnlineWeb_W_ID")
    private OnlineWeb onlineWeb;
    // Getters and setters

   /* public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public Set<Exam> getExams() {
        return exams;
    }

    public void setExams(Set<Exam> exams) {
        this.exams = exams;
    }*/
}