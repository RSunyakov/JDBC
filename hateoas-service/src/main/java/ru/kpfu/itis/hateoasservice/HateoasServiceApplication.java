package ru.kpfu.itis.hateoasservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.kpfu.itis.hateoasservice.models.*;
import ru.kpfu.itis.hateoasservice.repositories.*;

import java.util.Collections;

import static java.util.Arrays.asList;

@SpringBootApplication
public class HateoasServiceApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(HateoasServiceApplication.class, args);

        CoursesRepository coursesRepository = context.getBean(CoursesRepository.class);
        LessonsRepository lessonsRepository = context.getBean(LessonsRepository.class);
        StudentsRepository studentsRepository = context.getBean(StudentsRepository.class);
        ExamsRepository examsRepository = context.getBean(ExamsRepository.class);
        AuditoriumsRepository auditoriumsRepository = context.getBean(AuditoriumsRepository.class);
        LecturersRepository lecturersRepository = context.getBean(LecturersRepository.class);



        Lecturer marsel = Lecturer.builder()
                .firstName("Marsel")
                .lastName("Sidikov")
                .build();

        lecturersRepository.save(marsel);

        Course javaLab = Course.builder()
                .description("Курс по разработке на Java")
                .title("JavaLab")
                .state("Deleted")
                .lecturer(marsel)
                .build();

        Course dataLab = Course.builder()
                .description("Курс по Базам данных")
                .title("DataLab")
                .state("Draft")
                .build();

        coursesRepository.saveAll(asList(
                javaLab, dataLab
        ));

        Lecturer marsel_new = lecturersRepository.findById(1L).get();
        marsel_new.setCourses(Collections.singletonList(javaLab));
        lecturersRepository.save(marsel_new);


        Student daria = Student.builder()
                .firstName("Дария")
                .lastName("Шагиева")
                .courses(asList(javaLab, dataLab))
                .lecturers(Collections.singletonList(marsel))
                .build();

        Student emil = Student.builder()
                .firstName("Эмиль")
                .lastName("Аминов")
                .courses(Collections.singletonList(javaLab))
                .lecturers(Collections.singletonList(marsel))
                .build();
        
        studentsRepository.saveAll(asList(emil, daria));

        Lecturer marselStudent = lecturersRepository.findById(1L).get();
        marselStudent.setStudents(asList(daria, emil));
        lecturersRepository.save(marselStudent);

        Lesson firstJavaLabLesson = Lesson.builder()
                .title("Rest Data Repository")
                .rate(100)
                .course(javaLab)
                .build();

        Lesson secondJavaLabLesson = Lesson.builder()
                .title("HATEOAS")
                .rate(1)
                .course(javaLab)
                .build();

        Lesson firstDataLabLesson = Lesson.builder()
                .title("Парсим сайты")
                .rate(100)
                .course(dataLab)
                .build();

        Lesson secondDataLabLesson = Lesson.builder()
                .title("Что-то с таблицами они проходили")
                .rate(146)
                .course(dataLab)
                .build();

        lessonsRepository.saveAll(asList(firstJavaLabLesson,
                secondJavaLabLesson,
                firstDataLabLesson,
                secondDataLabLesson));

        Exam javaLabExam = Exam.builder()
                .name("JavaLabExam")
                .course(javaLab)
                .students(asList(daria, emil))
                .build();
        examsRepository.save(javaLabExam);

        Auditorium first = Auditorium.builder()
                .exams(Collections.singletonList(javaLabExam))
                .lessons(asList(firstDataLabLesson, firstJavaLabLesson))
                .name("1301")
                .build();
        auditoriumsRepository.save(first);

        Exam javaLabEx = examsRepository.findById(10L).get();
        javaLabEx.setAuditorium(first);
        javaLabEx.setState("Scheduled");
        Course javaLabCourse = coursesRepository.findById(2L).get();
        javaLabCourse.setExam(javaLabEx);
        coursesRepository.save(javaLabCourse);
        examsRepository.save(javaLabEx);


        Student emilUpdate = studentsRepository.findById(4L).get();
        emilUpdate.setExams(asList(javaLabEx));
        Student dariaUpdate = studentsRepository.findById(5L).get();
        dariaUpdate.setExams(asList(javaLabEx));
        studentsRepository.saveAll(asList(emilUpdate, dariaUpdate));

        Lesson javaLabLesson = lessonsRepository.findById(6L).get();
        javaLabLesson.setAuditoriums(Collections.singletonList(first));
        Lesson dataLabLesson = lessonsRepository.findById(8L).get();
        dataLabLesson.setAuditoriums(Collections.singletonList(first));
        lessonsRepository.saveAll(asList(javaLabLesson, dataLabLesson));

    }

}
