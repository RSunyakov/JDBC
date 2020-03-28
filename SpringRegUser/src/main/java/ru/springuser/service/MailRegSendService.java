package ru.springuser.service;

import freemarker.template.TemplateException;
import ru.springuser.dto.SignUpDto;
import ru.springuser.dto.UserDto;

import javax.mail.MessagingException;
import java.io.IOException;

public interface MailRegSendService {
    void sendReg(SignUpDto signUpDto) throws MessagingException, IOException, TemplateException;
}
