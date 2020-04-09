package ru.kpfu.itis.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.SignInDto;
import ru.kpfu.itis.dto.TokenDto;
import ru.kpfu.itis.service.user.SignInService;

@RestController
@RequestMapping(value = "/api")
public class SignInController {

    @Autowired
    private SignInService signInService;

    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public ResponseEntity<TokenDto> signIn(@RequestBody SignInDto signInData) {
        return ResponseEntity.ok(signInService.signIn(signInData));
    }
}
