package ru.kpfu.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kpfu.itis.dto.SignUpDto;
import ru.kpfu.itis.service.signup.SignUpService;

import javax.validation.Valid;

@Controller
public class SignupController {
    @Autowired
    private SignUpService signUpService;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView getSignUpPage(ModelAndView modelAndView) {
        modelAndView.addObject("signUpDto", new SignUpDto());
        modelAndView.setViewName("sign_up");
        return modelAndView;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signUp(
            RedirectAttributes redirectAttributes,
            @Valid @ModelAttribute("signUpDto") SignUpDto signUpDto,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "sign_up";
        } else {
            signUpService.signUp(signUpDto);
            redirectAttributes.addFlashAttribute("message", "<span style=\"color:green\">Пользователь \""+signUpDto.getEmail()+"\" успешно зарегистрирован</span>");
            return "redirect:"+ MvcUriComponentsBuilder.fromMappingName("SC#getSignUpPage").build();
        }
    }
}
