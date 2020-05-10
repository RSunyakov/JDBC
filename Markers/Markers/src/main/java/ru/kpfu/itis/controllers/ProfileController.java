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
import ru.kpfu.itis.service.auditorium.AuditoriumService;
import ru.kpfu.itis.service.user.UserAuditoriumService;
import ru.kpfu.itis.service.vkapi.VkApiService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private UserAuditoriumService userAuditoriumService;

    @Autowired
    private AuditoriumService auditoriumService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getProfile(Authentication authentication, Model model) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        model.addAttribute("user", userDetails.getUser());
        model.addAttribute("auditoriums", userDetails.getUser().getAuditoriumList());
        return "profile";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/profile/auditorium", method = RequestMethod.GET)
    public String getEditAuditorium(Model model) {
        model.addAttribute("auditorium", Auditorium.builder().build());
        model.addAttribute("auditoriums", auditoriumService.getAllNamesOfAuditoriums());
        return "edit_auditorium";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/profile/auditorium", method = RequestMethod.POST)
    public String editAuditorium(@ModelAttribute("auditorium") Auditorium auditorium, RedirectAttributes redirectAttributes, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        userAuditoriumService.add(userDetails.getUser(), auditorium);
        redirectAttributes.addFlashAttribute("message", "<span style=\"color:green\">Аудитория \"" + auditorium.getName() + "\" теперь отслеживается вами</span>");
        return "redirect:" + MvcUriComponentsBuilder.fromMappingName("PC#getProfile").build();
    }
}
