package ru.kpfu.itis.repositories.auditorium;

import org.springframework.stereotype.Component;
import ru.kpfu.itis.models.Auditorium;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class AuditoriumRepositoryEntityManagerImpl implements AuditoriumRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Optional<Auditorium> find(Long id) {
        Optional<Auditorium> auditoriumOptional = Optional.of(entityManager.find(Auditorium.class, id));
        if (auditoriumOptional.isPresent()) {
            auditoriumOptional.get().getUsers().size();
        }
        return auditoriumOptional;
    }

    @Override
    @Transactional
    public List<Auditorium> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Auditorium> criteriaQuery = criteriaBuilder.createQuery(Auditorium.class);
        Root<Auditorium> rootAuditorium = criteriaQuery.from(Auditorium.class);
        CriteriaQuery<Auditorium> all = criteriaQuery.select(rootAuditorium);
        TypedQuery<Auditorium> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    @Transactional
    public void save(Auditorium entity) {
        entityManager.persist(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (find(id).isPresent()) {
            entityManager.remove(find(id).get());
        } else {
            throw new IllegalArgumentException("Wrong id when delete auditorium");
        }
    }

    @Override
    @Transactional
    public void update(Auditorium auditorium) {
        entityManager.merge(auditorium);
    }

    @Override
    @Transactional
    public Optional<Auditorium> findByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Auditorium> criteriaQuery = criteriaBuilder.createQuery(Auditorium.class);
        Root<Auditorium> root = criteriaQuery.from(Auditorium.class);
        criteriaQuery.select(root);

        ParameterExpression<String> params = criteriaBuilder.parameter(String.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("name"), params));

        TypedQuery<Auditorium> query = entityManager.createQuery(criteriaQuery);
        query.setParameter(params, name);

        List<Auditorium> queryResult = query.getResultList();

        Auditorium returnUser = null;

        if (!queryResult.isEmpty()) {
            returnUser = queryResult.get(0);
        }
        return Optional.of(returnUser);
    }
}
