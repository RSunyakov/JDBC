package project.repositories;

import project.models.Site;
import project.models.Who;

import java.util.List;

public interface SiteRepository extends CrudRepository<Long, Site> {
    List<Site> getPagination(Integer page, Integer size, String sort);

    int update(Long id, Site siteData);

    List<Site> findAllForWho(Who who);
}
