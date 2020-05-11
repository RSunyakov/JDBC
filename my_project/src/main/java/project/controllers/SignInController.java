package project.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SignInController {

    @PreAuthorize("permitAll()")
    @GetMapping("/login")
    public ModelAndView getView(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView();
        if (authentication == null){
            modelAndView.setViewName("login");
        } else {
            modelAndView.setViewName("redirect:/profile");
        }
        return modelAndView;
    }



}
