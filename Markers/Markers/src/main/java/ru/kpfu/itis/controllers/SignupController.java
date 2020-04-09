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
import ru.kpfu.itis.service.user.SignUpService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class SignupController {
    @Autowired
    SignUpService signUpService;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView getSignUpPage(ModelAndView modelAndView) {
        modelAndView.addObject("signUpDto", new SignUpDto());
        modelAndView.setViewName("sign_up");
        return modelAndView;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signUp(
            RedirectAttributes redirectAttributes,
            HttpServletRequest request,
            @Valid @ModelAttribute("signUpDto") SignUpDto signUpDto,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            return "sign_up";
        } else {
            signUpService.SignUp(signUpDto);
            redirectAttributes.addFlashAttribute("message", "<span style=\"color:green\">User \""+signUpDto.getEmail()+"\" has been added successfully</span>");
            return "redirect:"+ MvcUriComponentsBuilder.fromMappingName("SC#getSignUpPage").build();
        }
    }
}
