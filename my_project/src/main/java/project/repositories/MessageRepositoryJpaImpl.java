package project.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.models.Message;
import project.services.MessageService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
public class MessageRepositoryJpaImpl implements MessageRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Message> find(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Message> findAll() {
        return null;
    }

    @Override
    @Transactional
    public Long save(Message entity) {
        entityManager.persist(entity);
        return entity.getId();
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }

    @Override
    @Transactional
    public List<Message> findAllForUser(Long userId) {
        return entityManager.createQuery("SELECT m FROM Message m where m.id_from = :userId or m.id_to = :userId order by m.send asc ")
                .setParameter("userId", userId).getResultList();
    }

    @Override
    @Transactional
    public void makeAllMessagesFromUserDelivered(Long userId) {
        entityManager.createQuery("update Message m set m.delivered = true where m.id_from = :userId")
                .setParameter("userId", userId).executeUpdate();
    }

    @Override
    @Transactional
    public List<Message> getAllNotDeliveredForUser(Long userId) {
        return entityManager.createQuery("select m from Message m where m.id_to = :userId and m.delivered = false order by m.send asc")
                .setParameter("userId", userId).getResultList();
    }

    @Override
    @Transactional
    public List<Long> getUsers() {
        return entityManager.createQuery("select distinct m.id_from from Message m where m.id_from is not null").getResultList();
    }

    @Override
    @Transactional
    public List<Message> getAllNotDeliveredFromUser(Long userId) {
        return entityManager.createQuery("select m from Message m where m.id_from = :userId and m.delivered = false order by m.send asc")
                .setParameter("userId", userId).getResultList();
    }

    @Override
    @Transactional
    public void makeAllMessagesToUserDelivered(Long userId) {
        entityManager.createQuery("update Message m set m.delivered = true where m.id_to = :userId")
                .setParameter("userId", userId).executeUpdate();
    }
}
