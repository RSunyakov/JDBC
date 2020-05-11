package project.repositories;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.models.Product;
import project.models.Site;
import project.models.Who;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
public class ProductRepositoryJpaImpl implements ProductRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Product> find(Long id) {
        return Optional.ofNullable(entityManager.find(Product.class, id));
    }

    @Override
    @Transactional
    public List<Product> findAll() {
        return entityManager.createQuery("SELECT p FROM Product p").getResultList();
    }

    @Override
    @Transactional
    public Long save(Product entity) {
        entityManager.persist(entity);
        return entity.getId();
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }


    @Override
    @Transactional
    public void truncate() {
        entityManager.createQuery("delete from Product").executeUpdate();
    }

    @Override
    public List<Product> findAllByStore(Site s) {
        return entityManager.createQuery("SELECT p FROM Product p where p.site = :s").setParameter("s", s).getResultList();
    }

    @Override
    public List<Product> sort(Boolean up) {
        if (up) {
            return entityManager.createQuery("SELECT p FROM Product p order by p.newPrice asc").getResultList();
        } else {
            return entityManager.createQuery("SELECT p FROM Product p order by p.newPrice desc").getResultList();
        }
    }

    @Override
    public List<Product> getPagination(Integer page, Integer size, String sort) {
        return entityManager.createQuery("SELECT p FROM Product p order by p." + sort + " asc")
                .setMaxResults(size).setFirstResult(calculateOffset(page, size))
                .setParameter("sort", sort).getResultList();
    }

    @Override
    public List<Product> findAllByStoreAndWho(Long store, Who who) {
        return entityManager.createQuery("SELECT p FROM Product p where p.site.id = :s and p.who = :who")
                .setParameter("s", store).setParameter("who", who).getResultList();

    }

    @Override
    public List<Product> findAllByWhoAndSort(boolean up, Who who) {
        if (up) {
            return entityManager.createQuery("SELECT p FROM Product p where p.who = : who order by cast(p.newPrice as int) asc")
                    .setParameter("who", who).getResultList();
        } else {
            return entityManager.createQuery("SELECT p FROM Product p where p.who = : who order by cast(p.newPrice as int) desc")
                    .setParameter("who", who).getResultList();
        }
    }

    @Override
    public List<Product> findAllByWho(Who who) {
        return entityManager.createQuery("SELECT p FROM Product p where p.who = :who").setParameter("who", who).getResultList();
    }

    @Override
    public List<Product> findAllByWhoAndStoreAndSort(boolean up, Who who, Long store) {
        if (up) {
            return entityManager.createQuery("SELECT p FROM Product p where p.who = : who and p.site.id = :s order by cast(p.newPrice as int) asc")
                    .setParameter("who", who).setParameter("s", store).getResultList();
        } else {
            return entityManager.createQuery("SELECT p FROM Product p where p.who = : who and p.site.id = :s order by cast(p.newPrice as int) desc")
                    .setParameter("who", who).setParameter("s", store).getResultList();
        }
    }

    private int calculateOffset(int page, int size) {
        return ((size * page) - size);
    }
}
