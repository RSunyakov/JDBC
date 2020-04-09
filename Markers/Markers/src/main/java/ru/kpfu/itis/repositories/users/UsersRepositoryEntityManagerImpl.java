package ru.kpfu.itis.repositories.users;

import org.springframework.stereotype.Component;
import ru.kpfu.itis.models.User;

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
public class UsersRepositoryEntityManagerImpl implements UsersRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Optional<User> find(Long id) {
        return Optional.of(entityManager.find(User.class, id));
    }

    @Override
    @Transactional
    public List<User> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> rootUser = criteriaQuery.from(User.class);
        CriteriaQuery<User> all = criteriaQuery.select(rootUser);
        TypedQuery<User> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    @Transactional
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (find(id).isPresent()) {
            entityManager.remove(find(id).get());
        } else {
            throw new IllegalArgumentException("Wrong id when delete user");
        }
    }

    @Override
    @Transactional
    public Optional<User> findByEmail(String email) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);

        ParameterExpression<String> params = criteriaBuilder.parameter(String.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("email"), params));

        TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
        query.setParameter(params, email);

        List<User> queryResult = query.getResultList();

        User returnUser = null;

        if (!queryResult.isEmpty()) {
            returnUser = queryResult.get(0);
        }

        return Optional.of(returnUser);
    }

    @Override
    @Transactional
    public void update(User user) {
        entityManager.merge(user);
    }
}
