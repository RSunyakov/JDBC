package ru.kpfu.itis.hateoasservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.kpfu.itis.hateoasservice.models.Exam;

import java.util.List;

public interface ExamsRepository extends PagingAndSortingRepository<Exam, Long> {
    @RestResource(path = "passed", rel = "passed")
    @Query("from Exam exam where exam.state = 'Passed'")
    Page<Exam> findAllPassed(Pageable pageable);

    @RestResource(path = "byName", rel = "name")
    List<Exam> findAllByName(String name);
}
