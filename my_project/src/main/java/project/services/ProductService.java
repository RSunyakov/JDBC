package project.services;

import project.models.Product;
import project.models.Who;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    public Long save(Product product);
    public List<Product> findAll();
    public void truncateTable();
    public List<Product> findAllByStore(Long id);
    public List<Product> sort(Boolean up);
    List<Product> getProducts(Integer page, Integer size, String sort);

    Optional<Product> find(Long id);

    List<Product> findAllByStoreAndWho(Long store, Who who);

    List<Product> findAllByWhoAndSort(boolean up, Who who);


    List<Product> findAllByWhoAndStoreAndSort(boolean up, Who who, Long store);

    List<Product> findAllByWho(Who who);
}
