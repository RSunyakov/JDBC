package ru.kpfu.itis.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private MessageContentService messageContentService;


    @Override
    public void sendImageUrl(String email, String fileName) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String text = messageContentService.getFileUrl(fileName, message);
            helper.setText(text, true);
            helper.setFrom("romansunyakov@gmail.com");
            helper.addTo(email);
            helper.setSubject("Image url");
        } catch (MessagingException e) {
            throw new IllegalStateException();
        }
        emailSender.send(message);
    }
}

