package project.services;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.models.User;
import project.models.VerificationToken;
import project.repositories.TokenRepository;
import project.repositories.UserRepository;

import java.time.Instant;
import java.util.*;

@Component
public class TokenServiceImpl implements TokenService {
    String key = "qwerty007";
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Long getUser(String token){
        if (token != null){
            Optional<VerificationToken> t = tokenRepository.findByToken(token);
            if (t.isPresent()){
                Date date = Date.from(Instant.now());
                System.out.println(date.toString());
                if (date.before(t.get().getExpiryDate())){
                    userRepository.verification(t.get().getUser_id());
                    return t.get().getUser_id();
                } else {
                    return null;
                }
            }else return null;
        }else return null;
    }

    @Override
    public String getToken(User user) {
        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("id", user.getId());
        tokenData.put("name", user.getName());
        tokenData.put("surname", user.getSurname());
        tokenData.put("email", user.getEmail());
        tokenData.put("password", user.getPassword());
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setClaims(tokenData);
        String token = jwtBuilder.signWith(SignatureAlgorithm.HS256, key).compact();
        return token;
    }

    @Override
    public String save(User model) {
        String token = getToken(model);
        tokenRepository.save(VerificationToken.builder()
                .user_id(model.getId())
                .token(token)
                .expiryDate(calculateExpiryDate())
                .build());
        return token;
    }




    private java.sql.Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        System.out.println("ДАТА " + cal.getTime());
        System.out.println(""+ new java.sql.Date(cal.getTimeInMillis()));
        return new java.sql.Date(cal.getTimeInMillis());
    }
}
