package ru.springuser.controller;

import freemarker.template.TemplateException;
import ru.springuser.dto.SignUpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.springuser.service.MailRegSendService;
import ru.springuser.service.SignUpService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/spring")
public class SignupController {

    @Autowired
    SignUpService signUpService;

    @Autowired
    MailRegSendService mailRegSendService;

    @RequestMapping(value = "/signup",  method = RequestMethod.GET)
    public String getSignUpPage(ModelMap map) {
        map.put("signUpDto", new SignUpDto());
        return "sign_up";
    }

    @RequestMapping(value = "/signup",  method = RequestMethod.POST)
    public String signUp(
            RedirectAttributes redirectAttributes,
            HttpServletRequest request,
            @Valid @ModelAttribute("signUpDto") SignUpDto signUpDto,
            BindingResult result,
            ModelMap map
    ) {
        if (result.hasErrors()) {
            return "sign_up";
        } else {
            signUpService.signUp(signUpDto);
            try {
                mailRegSendService.sendReg(signUpDto);
            } catch (MessagingException | IOException | TemplateException e) {
                throw new IllegalArgumentException(e);
            }
            redirectAttributes.addFlashAttribute("message", "<span style=\"color:green\">User \""+signUpDto.getEmail()+"\" has been added successfully</span>");
            return "redirect:"+ MvcUriComponentsBuilder.fromMappingName("SC#getSignUpPage").build();
        }
    }

    @RequestMapping(value = "/activate/{code}", method = RequestMethod.GET)
    public String activate(ModelMap map, @PathVariable String code) {
        map.put("signUpDto", new SignUpDto());
        map.put("activate", "email activate successful");
        boolean isActivated = signUpService.activateUser(code);
        if (isActivated) {
            map.put("emailmessage", "Email successfully activated");
        } else {
            map.put("emailmessage", "Activation code is not found");
        }
        return "file_upload";
    }
}
