package ru.springuser.service;

import freemarker.template.TemplateException;
import ru.springuser.model.Mail;

import javax.mail.MessagingException;
import java.io.IOException;

public interface MailService {
    void send(Mail mail) throws MessagingException, IOException, TemplateException;
}
