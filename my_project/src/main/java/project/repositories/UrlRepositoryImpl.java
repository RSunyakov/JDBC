package project.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import project.models.File;
import project.models.Site;
import project.models.Who;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UrlRepositoryImpl implements UrlRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Site> findSitesForWho(Who who) {
        return entityManager.createQuery("select distinct u.site from Url u where u.who = :who", Site.class)
                .setParameter("who", who).getResultList();
    }
}
