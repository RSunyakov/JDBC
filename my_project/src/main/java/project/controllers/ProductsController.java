package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.models.Product;
import project.models.Site;
import project.models.Who;
import project.services.AuthService;
import project.services.ProductService;
import project.services.SiteService;
import project.services.UserService;

import java.util.List;

@Controller
public class ProductsController {

    @Autowired
    ProductService productService;
    @Autowired
    SiteService siteService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @PreAuthorize("permitAll()")
    @GetMapping("/products_women")
    public ModelAndView getWomenPage(@RequestParam(required = false) Long store,
                                   @RequestParam(required = false) Integer select, Authentication authentication){
        return getProductsPage(store, select, Who.WOMAN,authentication).addObject("url","/products_women" )
                .addObject("who",Who.WOMAN);

    }

    @PreAuthorize("permitAll()")
    @GetMapping("/products_men")
    public ModelAndView getMenPage(@RequestParam(required = false) Long store,
                                   @RequestParam(required = false) Integer select, Authentication authentication){
        return getProductsPage(store, select, Who.MAN, authentication).addObject("url","/products_men" )
                .addObject("who",Who.MAN);

    }

    @PreAuthorize("permitAll()")
    @GetMapping("/products_kids")
    public ModelAndView getKidsPage(@RequestParam(required = false) Long store,
                                   @RequestParam(required = false) Integer select, Authentication authentication){
        return getProductsPage(store, select, Who.KIDS, authentication).addObject("url","/products_kids" )
                .addObject("who",Who.KIDS);
    }


    public ModelAndView getProductsPage(@RequestParam(required = false) Long store,
                                        @RequestParam(required = false) Integer select, Who who,
                                        Authentication authentication) {
        System.out.println("Products: " + authentication);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("product");
        List<Product> products;
        if (store != null) {
            products = productService.findAllByStoreAndWho(store, who);
        } else if (select != null){
            if (select == 2){
                products = productService.findAllByWhoAndSort(false, who);
            } else {
                products = productService.findAllByWhoAndSort(true, who);
            }
        } else {
            products = productService.findAllByWho(who);
        }
        String token = "";
        Integer auth = 0;
        if (authentication != null){
            token = authService.getToken(userService.findByEmail(authentication.getName()).get()).getToken();
            auth = 1;
        }
        modelAndView.addObject("token", token);
        modelAndView.addObject("auth", auth);
//        modelAndView.addObject("userId", userId);
        modelAndView.addObject("sites", siteService.findAllForWho(who));
        modelAndView.addObject("first", true);
        modelAndView.addObject("empty", "http://localhost:8080/files/empty.jpg");
        return modelAndView;
    }
}
