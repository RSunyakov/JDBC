package project.repositories;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.models.File;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component(value = "fileRepositoryJpa")
public class FileRepositoryJpaImpl implements FileRepository{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Optional<File> find(Long id) {
        return Optional.ofNullable(entityManager.find(File.class, id));
    }

    @Override
    public List<File> findAll() {
//        Query query = entityManager.createQuery("SELECT f FROM File f", File.class);
//        return (List<File>)query.getResultList();
        return null;
    }

    @Override
    @Transactional
    public Long save(File entity) {
        entityManager.persist(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        Optional<File> file = find(id);
        if (file.isPresent()) {
            entityManager.remove(file.get());
            return true;
        }
        return false;
    }

}
