package com.example.onlineexam.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Admin")
public class Admin {

    @Id
    @Column(name = "Admin_ID", length = 11)
    private String adminId;

    @Column(name = "Password")
    private String password;

    @Column(name = "OnlineWeb_W_ID")
    private String onlineWebWId;

    // Getters and setters
    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOnlineWebWId() {
        return onlineWebWId;
    }

    public void setOnlineWebWId(String onlineWebWId) {
        this.onlineWebWId = onlineWebWId;
    }
}