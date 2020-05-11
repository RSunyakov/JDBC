package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import project.dto.AuthDto;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private MessageContentService messageContentService;

    @Override
    public void sendConfirmation(String token, AuthDto authDto) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String text = messageContentService.getMailConfirmPage(authDto, token, message);
            helper.setText(text, true);
            helper.addTo(authDto.getEmail());
            helper.setSubject("Email confirmation");
        } catch (MessagingException e) {
            throw new IllegalStateException();
        }
        emailSender.send(message);
    }

    @Override
    public void sendImageUrl(String email, String fileName) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String text = messageContentService.getFileUrl(fileName, message);
            helper.setText(text, true);
            helper.addTo(email);
            helper.setSubject("Image url");
        } catch (MessagingException e) {
            throw new IllegalStateException();
        }
        emailSender.send(message);
    }
}
