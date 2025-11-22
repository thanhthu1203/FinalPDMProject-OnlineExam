package com.example.onlineexam.service;
import com.example.onlineexam.dto.ExamDashboardDTO;
import com.example.onlineexam.entity.Exam;
import com.example.onlineexam.entity.Result;
import com.example.onlineexam.entity.Student;
import com.example.onlineexam.repository.ResultRepository;
import com.example.onlineexam.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;
@Service
public class StudentService {

    private static final Logger log = LoggerFactory.getLogger(StudentService.class);

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
        log.info("Fetching dashboard for student: {}", studentId);

        List<ExamDashboardDTO> list = new ArrayList<>();

        try {
            Student student = findById(studentId);

            if (student == null) {
                log.warn("Student not found: {}", studentId);
                return list;
            }

            // Check if student has exams
            if (student.getExams() == null) {
                log.warn("Student.getExams() returned null for student: {}", studentId);
                return list;
            }

            if (student.getExams().isEmpty()) {
                log.info("No exams found for student: {}", studentId);
                return list;
            }

            log.info("Found {} exams for student: {}", student.getExams().size(), studentId);

            for (Exam exam : student.getExams()) {
                try {
                    if (exam == null) {
                        log.warn("Null exam found in student's exam list");
                        continue;
                    }

                    log.debug("Processing exam: {}", exam.getExamId());

                    // Safely get result
                    Result r = null;
                    Integer score = null;
                    try {
                        r = resultRepository.findByStudentIdAndExamId(studentId, exam.getExamId());
                        score = (r != null) ? r.getScore() : null;
                    } catch (Exception e) {
                        log.error("Error fetching result for exam: {}", exam.getExamId(), e);
                    }

                    // Safely get course name
                    String courseName = null;
                    try {
                        if (exam.getCourse() != null) {
                            courseName = exam.getCourse().getCName();
                        }
                    } catch (Exception e) {
                        log.error("Error fetching course name for exam: {}", exam.getExamId(), e);
                    }

                    // Create DTO with null-safe values
                    ExamDashboardDTO dto = new ExamDashboardDTO(
                            exam.getExamId() != null ? exam.getExamId() : "N/A",
                            courseName,
                            exam.getTestDateDATETIME(),
                            exam.getRoomNumber(),
                            score,
                            exam.getEType()
                    );

                    list.add(dto);
                    log.debug("Successfully added exam to dashboard: {}", exam.getExamId());

                } catch (Exception e) {
                    log.error("Error processing exam", e);
                    e.printStackTrace();
                    // Continue with next exam instead of failing completely
                }
            }

            log.info("Successfully returning {} exams for student: {}", list.size(), studentId);

        } catch (Exception e) {
            log.error("Critical error fetching dashboard for student: {}", studentId, e);
            e.printStackTrace();
            // Return empty list instead of throwing exception
        }

        return list;
    }
}