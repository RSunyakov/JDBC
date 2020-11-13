package ru.kpfu.itis.hateoasservice.service;

import ru.kpfu.itis.hateoasservice.models.Course;

public interface CoursesService {
    Course publish(Long courseId);
}
