package project.services;

import project.dto.AuthDto;

import javax.mail.internet.MimeMessage;

public interface MessageContentService {

    String getMailConfirmPage(AuthDto authDto, String token, MimeMessage message);
    String getFileUrl(String url, MimeMessage message);
}
