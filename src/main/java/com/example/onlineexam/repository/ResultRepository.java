package com.example.onlineexam.repository;

import com.example.onlineexam.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResultRepository extends JpaRepository<Result, Long> {

    @Query("SELECT r FROM Result r WHERE r.student.studentId = :studentId AND r.exam.examId = :examId")
    Result findByStudentIdAndExamId(@Param("studentId") String studentId, @Param("examId") String examId);
}