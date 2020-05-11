package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.models.Site;
import project.models.Who;
import project.repositories.SiteRepository;

import java.util.List;
import java.util.Optional;

@Component
public class SiteServiceImpl implements SiteService {
    @Autowired
    SiteRepository siteRepository;

    @Override
    public Long save(Site site) {
        return siteRepository.save(site);
    }

    @Override
    public List<Site> findAll() {
        return siteRepository.findAll();
    }

    @Override
    public List<Site> findAllForWho(Who who) {
        return siteRepository.findAllForWho(who);
    }

    @Override
    public Optional<Site> find(Long id) {
        return siteRepository.find(id);
    }

    @Override
    public List<Site> getSites(Integer page, Integer size, String sort) {
        return siteRepository.getPagination(page,size,sort);
    }

    @Override
    public Optional<Site> addSite(Site site) {

        return find(save(site));
    }

    @Override
    public boolean delete(Long id) {
        return siteRepository.delete(id);
    }

    @Override
    public int update(Long id, Site siteData) {
        return siteRepository.update(id, siteData);
    }
}
