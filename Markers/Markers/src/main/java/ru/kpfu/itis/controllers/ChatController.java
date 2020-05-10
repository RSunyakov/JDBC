package ru.kpfu.itis.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.kpfu.itis.security.details.UserDetailsImpl;

@Controller
public class ChatController {

    @RequestMapping(value = "/chat", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String getChatPage(Model model, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        model.addAttribute("userName", userDetails.getUser().getFirstName() + " " + userDetails.getUser().getLastName());
        model.addAttribute("userId", userDetails.getUser().getId());
        return "chat";
    }
}
