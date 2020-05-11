package project.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.models.Site;
import project.models.Who;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
public class SiteRepositoryJpaImpl implements SiteRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private UrlRepository urlRepository;

    @Override
    @Transactional
    public Optional<Site> find(Long id) {
        return Optional.ofNullable(entityManager.find(Site.class, id));
    }

    @Override
    @Transactional
    public List<Site> findAll() {
        return entityManager.createQuery("SELECT s FROM Site s").getResultList();
    }

    @Override
    @Transactional
    public Long save(Site entity) {
        entityManager.persist(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        Optional<Site> site = find(id);
        if (site.isPresent()) {
            entityManager.remove(site.get());
            return true;
        }
        return false;
    }


    @Override
    public List<Site> getPagination(Integer page, Integer size, String sort) {
        return entityManager.createQuery("SELECT s FROM Site s order by s." + sort + " asc")
                .setMaxResults(size).setFirstResult(calculateOffset(page, size))
                .getResultList();
    }

    @Override
    @Transactional
    public int update(Long id, Site site) {
        Optional<Site> siteCandidate = find(id);
        if (siteCandidate.isPresent()){
            return entityManager.createQuery("update Site s set s.store_name = :store_name, s.productsTag = :productsTag, " +
                    "s.productItemTag = :productItemTag, s.siteUrl = :siteUrl, s.productLinkTag = :productLinkTag, s.productLink = :productLink," +
                    "s.productImgTag = :productImgTag, s.productImg = :productImg, s.productName = :productName, s.newPriceTag = :newPriceTag," +
                    "s.oldPriceTag = :oldPriceTag, s.hasJS = :hasJS where s.id = :id")
                    .setParameter("store_name", site.getStore_name())
                    .setParameter("productsTag", site.getProductsTag())
                    .setParameter("productItemTag", site.getProductItemTag())
                    .setParameter("siteUrl", site.getSiteUrl())
                    .setParameter("productLinkTag", site.getProductLinkTag())
                    .setParameter("productLink",site.getProductLink())
                    .setParameter("productImgTag", site.getProductImgTag())
                    .setParameter("productImg", site.getProductImg())
                    .setParameter("productName",site.getProductName())
                    .setParameter("newPriceTag", site.getNewPriceTag())
                    .setParameter("oldPriceTag", site.getOldPriceTag())
                    .setParameter("hasJS",site.getHasJS())
                    .setParameter("id", id).executeUpdate();
        } else {
            return 0;
        }
    }

    @Override
    public List<Site> findAllForWho(Who who) {
        return urlRepository.findSitesForWho(who);
    }

    private int calculateOffset(int page, int size) {
        return ((size * page) - size);
    }
}
