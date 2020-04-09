package ru.kpfu.itis.service.user;

import ru.kpfu.itis.dto.SignInDto;
import ru.kpfu.itis.dto.TokenDto;

public interface SignInService {
    TokenDto signIn(SignInDto signInData);
}
