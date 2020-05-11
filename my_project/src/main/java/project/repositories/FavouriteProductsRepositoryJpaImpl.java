package project.repositories;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.models.FavouriteProduct;
import project.models.Site;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
public class FavouriteProductsRepositoryJpaImpl implements FavouriteProductsRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<FavouriteProduct> find(Long id) {
        return Optional.ofNullable(entityManager.find(FavouriteProduct.class, id));
    }

    @Override
    public List<FavouriteProduct> findAll() {
        return null;
    }

    @Override
    @Transactional
    public Long save(FavouriteProduct entity) {
        Optional<FavouriteProduct> favouriteProduct = findByUrl(entity.getProductUrl());
        if (!favouriteProduct.isPresent()){
            entityManager.persist(entity);
            return entity.getId();
        }
        return 0L;
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }

    @Override
    public List<FavouriteProduct> findAllByUserId(Long userId) {
        return entityManager.createQuery("SELECT f FROM FavouriteProduct f where f.user_id = :userId")
                .setParameter("userId", userId).getResultList();
    }

    @Override
    @Transactional
    public boolean delete(Long productId, Long userId) {
        Optional<FavouriteProduct> favouriteProduct = find(productId);
        if (favouriteProduct.isPresent()) {
            entityManager.createQuery("delete from FavouriteProduct f where f.user_id = :userId and f.id = :productId")
                    .setParameter("userId", userId).setParameter("productId", productId).executeUpdate();
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Optional<FavouriteProduct> findByUrl(String url) {
        List<FavouriteProduct> favouriteProducts = entityManager.createQuery("SELECT f FROM FavouriteProduct f where f.productUrl = :url")
                .setParameter("url", url).getResultList();
        if (favouriteProducts.isEmpty()){
            return Optional.empty();
        }
        return Optional.ofNullable(favouriteProducts.get(0));
    }
}
