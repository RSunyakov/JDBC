package ru.springuser.security.authprovider;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.springuser.model.User;
import ru.springuser.repositories.UsersRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class RussianAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private HttpServletRequest request;


    @Autowired
    private UsersRepository repository;

    @Autowired
   private PasswordEncoder encoder;

    @Autowired
   private DatabaseReader databaseReader;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        String ip = getClientIP();
        String country = null;
        try {
            country = databaseReader.country(InetAddress.getByName(ip)).getCountry().toString();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        } catch (GeoIp2Exception e) {
            e.printStackTrace();
        }
        if (!country.equals("Russian")) {
            throw new BadCredentialsException("No russian country");
        }
        Optional<User> optionalUser = repository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String hashPassword = encoder.encode(password);
            if (hashPassword.equals(user.getHashPassword())) {
                return new UsernamePasswordAuthenticationToken(email, password, new ArrayList<>());
            } else {
                throw new BadCredentialsException("Bad credentials");
            }
        }  else {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }

    private String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
