package ru.kpfu.itis.repositories.messages;

import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.models.Messages;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class MessagesRepositoryEntityManagerImpl implements MessagesRepository{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Messages> find(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Messages> findAll() {
        return null;
    }

    @Override
    @Transactional
    public void save(Messages entity) {
        entityManager.persist(entity);
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    @Transactional
    public List<Messages> findAllForUser(Long userId) {
        return entityManager.createQuery("SELECT m FROM Messages m where m.id_from = :userId or m.id_to = :userId order by m.send asc ")
                .setParameter("userId", userId).getResultList();
    }

    @Override
    @Transactional
    public void makeAllMessagesFromUserDelivered(Long userId) {
        entityManager.createQuery("update Messages m set m.delivered = true where m.id_from = :userId")
                .setParameter("userId", userId).executeUpdate();
    }

    @Override
    @Transactional
    public List<Messages> getAllNotDeliveredForUser(Long userId) {
        return entityManager.createQuery("select m from Messages m where m.id_to = :userId and m.delivered = false order by m.send asc")
                .setParameter("userId", userId).getResultList();
    }

    @Override
    @Transactional
    public List<Long> getUsers() {
        return entityManager.createQuery("select distinct m.id_from from Messages m where m.id_from is not null").getResultList();
    }

    @Override
    @Transactional
    public List<Messages> getAllNotDeliveredFromUser(Long userId) {
        return entityManager.createQuery("select m from Messages m where m.id_from = :userId and m.delivered = false order by m.send asc")
                .setParameter("userId", userId).getResultList();
    }

    @Override
    @Transactional
    public void makeAllMessagesToUserDelivered(Long userId) {
        entityManager.createQuery("update Messages m set m.delivered = true where m.id_to = :userId")
                .setParameter("userId", userId).executeUpdate();
    }


}