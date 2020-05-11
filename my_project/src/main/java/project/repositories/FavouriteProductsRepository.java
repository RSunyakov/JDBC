package project.repositories;

import project.models.FavouriteProduct;

import java.util.List;
import java.util.Optional;

public interface FavouriteProductsRepository extends CrudRepository<Long, FavouriteProduct> {
    List<FavouriteProduct> findAllByUserId(Long userId);

    boolean delete(Long productId, Long userId);

    Optional<FavouriteProduct> findByUrl(String url);
}
