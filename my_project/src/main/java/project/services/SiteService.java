package project.services;

import project.models.Site;
import project.models.Who;

import java.util.List;
import java.util.Optional;

public interface SiteService {

    public Long save(Site site);
    public List<Site> findAll();
    public List<Site> findAllForWho(Who who);

    Optional<Site> find(Long id);

    List<Site> getSites(Integer page, Integer size, String sort);

    Optional<Site> addSite(Site site);

    boolean delete(Long id);

    int update(Long id, Site siteData);
}
