package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.services.AuthService;
import project.services.UserService;

import java.util.Locale;

@Controller
public class FavouritesController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;



    @GetMapping("/favourites")
    public String getFavourites(Model model, Authentication authentication){
        if (authentication == null) {
            return "redirect:/login";
        }
        String token = "";
        if (authentication != null){
            token = authService.getToken(userService.findByEmail(authentication.getName()).get()).getToken();
        }
        model.addAttribute("token", token);
        return "favourites";
    }
}

