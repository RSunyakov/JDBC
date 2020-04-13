package ru.kpfu.itis.repositories.message;

import org.springframework.stereotype.Component;
import ru.kpfu.itis.models.Message;
import ru.kpfu.itis.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class MessageRepositoryEntityManagerImpl implements MessagesRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void update(Message message) {
        entityManager.merge(message);
    }

    @Override
    @Transactional
    public Optional<Message> find(Long id) {
        return Optional.of(entityManager.find(Message.class, id));
    }

    @Override
    @Transactional
    public List<Message> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);
        Root<Message> rootMessage = criteriaQuery.from(Message.class);
        CriteriaQuery<Message> all = criteriaQuery.select(rootMessage);
        TypedQuery<Message> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    @Transactional
    public void save(Message entity) {
        entityManager.persist(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (find(id).isPresent()) {
            entityManager.remove(find(id).get());
        } else {
            throw new IllegalArgumentException("Wrong id when delete message");
        }
    }

    @Override
    public List<Message> findByUserId(Long userId) {
        Query query = entityManager.createQuery("Select message FROM Message message WHERE message.user.id = :userId");
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
