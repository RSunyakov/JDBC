package ru.kpfu.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kpfu.itis.models.Auditorium;
import ru.kpfu.itis.security.details.UserDetailsImpl;
import ru.kpfu.itis.service.user.UserAuditoriumService;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class ProfileController {
    private final String[] auditoriums = {"1501", "1502", "1503", "1504", "1505", "1506", "1507", "1508"};

    @Autowired
    UserAuditoriumService userAuditoriumService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getProfile(Authentication authentication, Model model) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        model.addAttribute("user", userDetails.getUser());
        return "profile";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/profile/auditorium", method = RequestMethod.GET)
    public String getEditAuditorium(Model model) {
        model.addAttribute("auditorium", Auditorium.builder().build());
        model.addAttribute("auditoriums", Arrays.asList(auditoriums));
        return "edit_auditorium";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/profile/auditorium", method = RequestMethod.POST)
    public String editAuditorium(@ModelAttribute("auditorium") Auditorium auditorium, RedirectAttributes redirectAttributes, Authentication authentication) {
        userAuditoriumService.add(authentication, auditorium);
        redirectAttributes.addFlashAttribute("message", "<span style=\"color:green\">Аудитория \"" + auditorium.getName() + "\" теперь отслеживается вами</span>");
        return "redirect:" + MvcUriComponentsBuilder.fromMappingName("PC#getProfile").build();
    }
}
