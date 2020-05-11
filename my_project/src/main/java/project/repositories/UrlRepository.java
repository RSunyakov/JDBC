package project.repositories;

import project.models.Site;
import project.models.Who;

import java.util.List;

public interface UrlRepository {
    List<Site> findSitesForWho(Who who);
}
