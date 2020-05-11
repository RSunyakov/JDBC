package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.models.FavouriteProduct;
import project.repositories.FavouriteProductsRepository;
import project.repositories.FavouriteProductsRepositoryJpaImpl;

import java.util.List;

@Component
public class FavouriteProductsServiceImpl implements FavouriteProductsService {
    @Autowired
    private FavouriteProductsRepository favouriteProductsRepository;
    @Override
    public Long save(FavouriteProduct product) {
        return favouriteProductsRepository.save(product);
    }

    @Override
    public List<FavouriteProduct> findAll(Long userId) {
        return favouriteProductsRepository.findAllByUserId(userId);
    }

    @Override
    public boolean delete(Long productId, Long userId) {
        return favouriteProductsRepository.delete(productId, userId);
    }
}
