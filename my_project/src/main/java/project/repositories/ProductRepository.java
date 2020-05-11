package project.repositories;

import project.models.Product;
import project.models.Site;
import project.models.Who;

import java.util.List;

public interface ProductRepository extends CrudRepository<Long, Product> {
    void truncate();

    List<Product> findAllByStore(Site site);

    List<Product> sort(Boolean up);

    List<Product> getPagination(Integer page, Integer size, String sort);

    List<Product> findAllByStoreAndWho(Long store, Who i);

    List findAllByWhoAndSort(boolean up, Who who);

    List<Product> findAllByWho(Who who);

    List<Product> findAllByWhoAndStoreAndSort(boolean up, Who who, Long store);
}
