package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.dto.ResponseProductDto;
import project.dto.ResponseSiteDto;
import project.dto.ResponseSitesDto;
import project.dto.SiteDto;
import project.models.Site;
import project.services.SiteService;

@RestController
@RequestMapping("/api/sites-management")
@PreAuthorize("hasAuthority('ADMIN')")
public class SitesRestController {

    @Autowired
    private SiteService siteService;

    @PostMapping("/sites")
    public ResponseSiteDto addUser(@RequestBody Site siteData) {
        return ResponseSiteDto.builder()
                .data(siteService.addSite(siteData).get())
                .build();
    }

    @GetMapping("/sites/pagination")
    public ResponseSitesDto getSites(@RequestParam("page") Integer page,
                                     @RequestParam("size") Integer size,
                                     @RequestParam("sort") String sort)  {
        return ResponseSitesDto.builder()
                .data(siteService.getSites(page, size, sort))
                .build();
    }

    @GetMapping("/sites")
    public ResponseSitesDto getAllSites()  {
        return ResponseSitesDto.builder()
                .data(siteService.findAll())
                .build();
    }

    @GetMapping("/sites/{site-id}")
    public ResponseSiteDto getSite(@PathVariable("site-id") Long id) {
        return ResponseSiteDto.builder()
                .data(siteService.find(id).get())
                .build();
    }

    @DeleteMapping("/sites/{site-id}")
    public boolean deleteSite(@PathVariable("site-id") Long id){
        return siteService.delete(id);
    }

    @PutMapping("/sites/{site-id}")
    public String updateSite(@PathVariable("site-id") Long id, @RequestBody Site siteData){
        return siteService.update(id, siteData) +  " record(s) updated";
    }
}

