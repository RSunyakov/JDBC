package project.services;

import project.dto.SignInDto;
import project.dto.TokenDto;
import project.models.User;

public interface AuthService {
    public Long signUp(User model);

    TokenDto signIn(SignInDto signInData);

    TokenDto getToken(User user);
}
