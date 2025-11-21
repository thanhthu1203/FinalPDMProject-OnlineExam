package com.example.onlineexam.service;

import com.example.onlineexam.dto.ExamDashboardDTO;
import com.example.onlineexam.entity.Exam;
import com.example.onlineexam.entity.Result;
import com.example.onlineexam.entity.Student;
import com.example.onlineexam.repository.ResultRepository;
import com.example.onlineexam.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final ResultRepository resultRepository;

    public StudentService(StudentRepository studentRepository, ResultRepository resultRepository) {
        this.studentRepository = studentRepository;
        this.resultRepository = resultRepository;
    }

    public Student findById(String id) {
        return studentRepository.findById(id).orElse(null);
    }

    public List<ExamDashboardDTO> getDashboardForStudent(String studentId) {
        Student student = findById(studentId);
        List<ExamDashboardDTO> list = new ArrayList<>();
        if (student == null) return list;

        for (Exam exam : student.getExams()) {
            Result r = resultRepository.findByStudentIdAndExamId(studentId, exam.getExamId());
            Integer score = r != null ? r.getScore() : null;
            String courseName = exam.getCourse() != null ? exam.getCourse().getcName() : null;
            list.add(new ExamDashboardDTO(
                    exam.getExamId(),
                    courseName,
                    exam.getTestDateDATETIME(),
                    exam.getRoomNumber(),
                    score,
                    exam.geteType()
            ));
        }
        return list;
    }
}