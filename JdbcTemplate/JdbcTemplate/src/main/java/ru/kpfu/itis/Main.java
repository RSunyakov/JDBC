package ru.kpfu.itis;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.kpfu.itis.models.Course;
import ru.kpfu.itis.repositories.CoursesRepository;
import ru.kpfu.itis.repositories.CoursesRepositoryJdbcTemplateImpl;

import java.util.Optional;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        HikariConfig config = new HikariConfig();
        config.setUsername("postgres");
        config.setPassword("123");
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/jdbc_template_test");
        HikariDataSource dataSource = new HikariDataSource(config);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        CoursesRepository coursesRepository = new CoursesRepositoryJdbcTemplateImpl(jdbcTemplate);
        Optional<Course> courseOptional = coursesRepository.find(1L);
        System.out.println(courseOptional.get());
    }
}
