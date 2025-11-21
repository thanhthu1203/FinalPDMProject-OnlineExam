package com.example.onlineexam.repository;

import com.example.onlineexam.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, String> {
}