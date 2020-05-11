package project.services;

import project.models.FavouriteProduct;

import java.util.List;

public interface FavouriteProductsService {
    Long save(FavouriteProduct product);
    List<FavouriteProduct> findAll(Long userId);

    boolean delete(Long productId, Long userId);
}
