package ru.kpfu.itis.annotations.model;

import ru.kpfu.itis.annotations.annotation.HtmlForm;
import ru.kpfu.itis.annotations.annotation.HtmlInput;

@HtmlForm(method = "post", action = "/user")
public class User {
    @HtmlInput(name = "login", placeholder = "Логин")
    private String login;
    @HtmlInput(name = "email", placeholder = "Email")
    private String email;
    @HtmlInput(type = "password", name = "password", placeholder = "Пароль")
    private String password;
}
