package ru.springuser.service;

import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import ru.springuser.dto.SignUpDto;
import ru.springuser.dto.UserDto;
import ru.springuser.model.Mail;
import ru.springuser.model.User;
import ru.springuser.repositories.UsersRepository;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Optional;

@Service
public class MailRegSendSerivceImpl implements MailRegSendService {
    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    UsersRepository repository;
    @Autowired
    MailService mailService;

    @Override
    public void sendReg(SignUpDto signUpDto) throws MessagingException, IOException, TemplateException {
        Optional<User> user = repository.findByEmail(signUpDto.getEmail());
        ModelMap modelMap = new ModelMap();
        if (user.isPresent()) {
            modelMap.put("user", user.get());
            Mail mail = new Mail();
            mail.setFrom(username);
            mail.setTo(signUpDto.getEmail());
            mail.setSubject("Email Activation");
            mail.setModel(modelMap);
            mailService.send(mail);
        }
    }
}
