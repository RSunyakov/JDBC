package ru.kpfu.itis.hateoasservice.service;

import ru.kpfu.itis.hateoasservice.models.Exam;

public interface ExamService {
    Exam pass(Long examId);
    Exam failure(Long examId);
}
