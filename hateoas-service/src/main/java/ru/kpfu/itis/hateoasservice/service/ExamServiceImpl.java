package ru.kpfu.itis.hateoasservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.hateoasservice.models.Course;
import ru.kpfu.itis.hateoasservice.models.Exam;
import ru.kpfu.itis.hateoasservice.models.Student;
import ru.kpfu.itis.hateoasservice.repositories.ExamsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamsRepository examsRepository;

    @Override
    public Exam pass(Long examId) {
        Exam exam = examsRepository.findById(examId).orElseThrow(IllegalAccessError::new);
        exam.pass();
        List<Student> students = exam.getStudents();
        for (Student student : students) {
            List<Course> courses = student.getCourses();
            for (int j = 0; j < courses.size(); j++) {
                if (courses.get(j).getId().equals(exam.getCourse().getId())) {
                    courses.remove(j);
                    break;
                }
            }
        }
        examsRepository.save(exam);
        return exam;
    }

    @Override
    public Exam failure(Long examId) {
        Exam exam = examsRepository.findById(examId).orElseThrow(IllegalAccessError::new);
        if (exam.getState().equals("Scheduled")) {
            exam.setState("Failure");
        } else if (exam.getState().equals("Deleted")) {
            throw new IllegalStateException();
        }
        examsRepository.save(exam);
        return exam;
    }
}
