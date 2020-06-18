package ru.kpfu.itis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.kpfu.itis.repositories.CoursesRepository;

public class MainWithContext {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        CoursesRepository coursesRepository = context.getBean(CoursesRepository.class);
        System.out.println(coursesRepository.findAll());
    }
}
