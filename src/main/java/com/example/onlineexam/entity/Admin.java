package com.example.onlineexam.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Admin")
public class Admin {

    @Id
    @Column(name = "Admin_ID", length = 11)
    private String adminId;

    @Column(name = "Password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "OnlineWeb_W_ID")
    private OnlineWeb onlineWeb;

    // Getters and setters
    /*public String getAdminId() {
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
    }*/
}