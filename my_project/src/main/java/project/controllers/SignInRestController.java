package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.dto.SignInDto;
import project.dto.TokenDto;
import project.services.AuthService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class SignInRestController {

    @Autowired
    private AuthService authService;

    @PreAuthorize("permitAll()")
    @PostMapping("/api/signIn")
    public ResponseEntity<TokenDto> signIn(@RequestBody SignInDto signInData, HttpServletResponse response, HttpServletRequest request) {
        TokenDto token = authService.signIn(signInData);
        return ResponseEntity.ok(token);
    }
}
