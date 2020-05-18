package ru.kpfu.itis.service.email;

import javax.mail.internet.MimeMessage;

public interface MessageContentService {
    String getFileUrl(String url, MimeMessage message);
}
