package ru.kpfu.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kpfu.itis.dto.SignInDto;
import ru.kpfu.itis.dto.SignUpDto;
import ru.kpfu.itis.service.signin.SignInService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class SigninController {

    @Autowired
    private SignInService signInService;

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public ModelAndView getSignInPage(ModelAndView modelAndView) {
        modelAndView.addObject("signInDto", new SignInDto());
        modelAndView.setViewName("sign_in");
        return modelAndView;
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public String signIn(
            RedirectAttributes redirectAttributes,
            HttpServletResponse httpServletResponse,
            @Valid @ModelAttribute("signInDto") SignInDto signInDto,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "sign_in";
        } else {
            if (signInService.signIn(signInDto, httpServletResponse)) {
                redirectAttributes.addFlashAttribute("success", "Пользователь " + signInDto.getEmail() + "  успешно авторизовался");
            } else {
                redirectAttributes.addFlashAttribute("failure", "Неверный логин/пароль");
            }
            return "redirect:"+ MvcUriComponentsBuilder.fromMappingName("SC#getSignInPage").build();
        }
    }
}
