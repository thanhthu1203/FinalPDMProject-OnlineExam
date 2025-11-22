package com.example.onlineexam.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "onlineweb")
public class OnlineWeb {

    @Id
    @Column(name = "W_ID")
    private String wId;

    @Column(name = "WName")
    private String wName;

    // Relationship: One OnlineWeb -> Many Admins
    @OneToMany(mappedBy = "onlineWeb")
    private List<Admin> admins;

    // Relationship: One OnlineWeb -> Many Courses (based on the dashed line in ERD)
    @OneToMany(mappedBy = "onlineWeb")
    private List<Course> courses;

    // Getters and Setters...
}