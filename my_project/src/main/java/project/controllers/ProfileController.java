package project.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import project.dto.ProfileForm;
import project.security.details.UserDetailsImpl;

import javax.validation.Valid;

@Controller
public class ProfileController {

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String getProfile(Model model) {
        model.addAttribute("profileForm", new ProfileForm());
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(Authentication authentication, @Valid ProfileForm form, BindingResult bindingResult, Model model) {
        System.out.println(form);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        model.addAttribute("user", userDetails.getUser());
        System.out.println(bindingResult.getAllErrors());
        model.addAttribute("profileForm", form);
        return "profile";
    }
}
