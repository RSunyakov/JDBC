package project.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import project.dto.SignInDto;
import project.dto.TokenDto;
import project.dto.AuthDto;
import project.models.Role;
import project.models.State;
import project.models.User;
import project.repositories.UserRepository;

import java.util.Optional;
import java.util.concurrent.ExecutorService;

@Component
public class AuthServiceImpl implements AuthService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ExecutorService executorService;
    @Value("${jwt.secret}")
    private String secret;

    @Override
    public Long signUp(User model) {
        model.setPassword(passwordEncoder.encode(model.getPassword()));
        model.setState(State.NOT_CONFIRMED);
        model.setRole(Role.USER);
        model.setId(userRepository.save(model));
        String token = tokenService.save(model);
        executorService.submit(() -> emailService.sendConfirmation(token, AuthDto.fromUser(model)));
        return model.getId();
    }

    @Override
    public TokenDto signIn(SignInDto signInData) {
        Optional<User> userOptional = userRepository.findUserByEmail(signInData.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(signInData.getPassword(), user.getPassword())) {
                return getToken(user);
            } else throw new AccessDeniedException("Wrong email/password");
        } else throw new AccessDeniedException("User not found");
    }

    @Override
    public TokenDto getToken(User user) {
        String token = Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("role", user.getRole().name())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        return new TokenDto(token);
    }

}
