package ru.kpfu.itis.hateoasservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.hateoasservice.models.Course;
import ru.kpfu.itis.hateoasservice.repositories.CoursesRepository;

@Service
public class CoursesServiceImpl implements CoursesService{

    @Autowired
    private CoursesRepository coursesRepository;

    @Override
    public Course publish(Long courseId) {
        Course course = coursesRepository.findById(courseId).orElseThrow(IllegalAccessError::new);
        course.publish();
        coursesRepository.save(course);
        return course;
    }
}
