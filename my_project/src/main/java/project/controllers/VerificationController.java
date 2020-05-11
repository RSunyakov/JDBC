package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.services.TokenService;

import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("v")
public class VerificationController {
    @Autowired
    private TokenService tokenService;

    @PreAuthorize("permitAll()")
    @GetMapping("/verification")
    public ModelAndView checkVerification(@RequestParam String t, HttpSession session){
        String token = t;
        ModelAndView modelAndView = new ModelAndView();
        Long userId = tokenService.getUser(token);
        String ans = "";
        if (userId != null){
            ans = "Почта успешно подтверждена! Войдите в свой аккаунт";
        } else {
            ans = "Неверная ссылка или истек срок использования";
        }
        modelAndView.addObject("answer", ans);
        modelAndView.setViewName("verification");
        return modelAndView;
    }
}
