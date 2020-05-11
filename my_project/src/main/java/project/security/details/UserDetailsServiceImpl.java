package project.security.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import project.dto.TokenDto;
import project.models.User;
import project.repositories.UserRepository;
import project.services.AuthService;

import javax.servlet.http.Cookie;
import java.util.Optional;

@Service(value = "customUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if (userOptional.isPresent()){
            User user = userOptional.get();
//            TokenDto token = authService.getToken(user);
//            Cookie cookie = new Cookie("token", token.getToken());
//            cookie.setMaxAge(60*3);
//            response.addCookie(cookie);
//            cookie.setSecure(true);
//            cookie.setHttpOnly(true);
            return new UserDetailsImpl(user);
        } throw new UsernameNotFoundException("User not found");
    }
}
