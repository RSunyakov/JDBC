package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.models.Product;
import project.models.Site;
import project.models.Who;
import project.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SiteService siteService;

    @Override
    public Long save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void truncateTable() {
        productRepository.truncate();
    }

    @Override
    public List<Product> findAllByStore(Long id) {
        Optional<Site> site = siteService.find(id);
        if (site.isPresent()) {
            return productRepository.findAllByStore(site.get());
        }
        return null;
    }

    @Override
    public List<Product> sort(Boolean up) {
        return productRepository.sort(up);
    }

    @Override
    public List<Product> getProducts(Integer page, Integer size, String sort) {
        return productRepository.getPagination(page, size, sort);
    }

    @Override
    public Optional<Product> find(Long id) {
        return productRepository.find(id);
    }

    @Override
    public List<Product> findAllByStoreAndWho(Long store, Who who) {
        return productRepository.findAllByStoreAndWho(store,who);
    }

    @Override
    public List<Product> findAllByWhoAndSort(boolean up, Who who) {
        return productRepository.findAllByWhoAndSort(up, who);
    }

    @Override
    public List<Product> findAllByWhoAndStoreAndSort(boolean up, Who who, Long store) {
        return productRepository.findAllByWhoAndStoreAndSort(up, who, store);
    }

    @Override
    public List<Product> findAllByWho(Who who) {
        return productRepository.findAllByWho(who);
    }


}
