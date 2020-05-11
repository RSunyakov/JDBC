package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.dto.ProfileForm;
import project.dto.SignUpForm;
import project.models.User;
import project.security.details.UserDetailsImpl;
import project.services.AuthService;

import javax.validation.Valid;

@Controller
public class SignUpController {

    @Autowired
    private AuthService authService;


    @PreAuthorize("permitAll()")
    @GetMapping("/registration")
    public String getView(Authentication authentication, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        if (authentication != null) {
            return "redirect:/profile";
        } else {
            model.addAttribute("signUpForm", new SignUpForm());
            return "signUp";
        }
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/registration")
    public String getCourse(@RequestParam String name, @RequestParam String surname,
                            @RequestParam String email, @RequestParam String password,
                            @Valid SignUpForm form, BindingResult bindingResult, Model model) {
        System.out.println(form);
        if (!bindingResult.hasErrors()) {
            Long id = authService.signUp(User.builder()
                    .name(name)
                    .surname(surname)
                    .email(email)
                    .password(password)
                    .build());
            return "redirect:/login";
        } else {
            System.out.println(bindingResult.getAllErrors());
            model.addAttribute("signUpForm", form);
            return "signUp";
        }
    }

//    @PostMapping("/profile")
//    public String updateProfile(Authentication authentication, @Valid ProfileForm form, BindingResult bindingResult, Model model) {
//        System.out.println(form);
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        model.addAttribute("user", userDetails.getUser());
//        System.out.println(bindingResult.getAllErrors());
//        model.addAttribute("profileForm", form);
//        return "profile";
//    }

}
