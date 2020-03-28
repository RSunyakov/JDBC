package ru.springuser.service;

import ru.springuser.dto.SignUpDto;

import javax.servlet.http.HttpServletRequest;

public interface SignUpService {
    void signUp(SignUpDto form);
    boolean activateUser(String code);
}
