package project.services;

import project.dto.AuthDto;

public interface EmailService {
    public void sendConfirmation(String token, AuthDto authDto);
    public void sendImageUrl(String email, String fileName);
}
