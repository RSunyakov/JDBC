package ru.kpfu.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.kpfu.itis.models.User;
import ru.kpfu.itis.service.user.UserFromCookieService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ChatController {
    @Autowired
    private UserFromCookieService userFromCookieService;



    @RequestMapping(value = "/chat/{roomId}", method = RequestMethod.GET)
    public String getChatPage(Model model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable String roomId) {
        model.addAttribute("user", userFromCookieService.getUser(httpServletRequest, httpServletResponse));
        model.addAttribute("roomId", roomId);
        return "chat";
    }
}
