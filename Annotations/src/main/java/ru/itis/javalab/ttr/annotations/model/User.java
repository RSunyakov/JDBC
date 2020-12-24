package ru.itis.javalab.ttr.annotations.model;

import ru.itis.javalab.ttr.annotations.annotation.HtmlForm;
import ru.itis.javalab.ttr.annotations.annotation.HtmlInput;

@HtmlForm(method = "post", action = "/user")
public class User {
    @HtmlInput(name = "login", placeholder = "login")
    private String login;
    @HtmlInput(name = "email", placeholder = "Email")
    private String email;
    @HtmlInput(type = "password", name = "password", placeholder = "Пароль")
    private String password;
}
