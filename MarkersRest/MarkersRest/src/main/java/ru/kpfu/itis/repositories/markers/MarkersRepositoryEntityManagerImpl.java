package ru.kpfu.itis.repositories.markers;

import org.springframework.stereotype.Component;
import ru.kpfu.itis.models.Auditorium;
import ru.kpfu.itis.models.Marker;
import ru.kpfu.itis.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class MarkersRepositoryEntityManagerImpl implements MarkersRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Optional<Marker> find(Long id) {
        return Optional.of(entityManager.find(Marker.class, id));
    }

    @Override
    @Transactional
    public List<Marker> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Marker> criteriaQuery = criteriaBuilder.createQuery(Marker.class);
        Root<Marker> rootMarker = criteriaQuery.from(Marker.class);
        CriteriaQuery<Marker> all = criteriaQuery.select(rootMarker);
        TypedQuery<Marker> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    @Transactional
    public void save(Marker entity) {
        entityManager.persist(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (find(id).isPresent()) {
            entityManager.remove(find(id).get());
        } else {
            throw new IllegalArgumentException("Wrong id when delete marker");
        }
    }

    @Override
    @Transactional
    public void update(Marker marker) {
        entityManager.merge(marker);
    }

    @Override
    @Transactional
    public List<Marker> findByAuditoriumId(Long auditoriumId) {
        Query query = entityManager.createQuery("Select marker FROM Marker marker WHERE marker.auditorium.id = :auditoriumId");
        query.setParameter("auditoriumId", auditoriumId);
        return query.getResultList();
    }
}
