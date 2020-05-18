package ru.kpfu.itis.repositories.file;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.models.File;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
public class FileRepositoryEntityManagerImpl implements FileRepository {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional
    public Optional<File> find(Long id) {
        return Optional.ofNullable(entityManager.find(File.class, id));
    }

    @Override
    public List<File> findAll() {
        return null;
    }

    @Override
    @Transactional
    public void save(File entity) {
        entityManager.persist(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<File> file = find(id);
        if (file.isPresent()) {
            entityManager.remove(file.get());
        }
    }

}
