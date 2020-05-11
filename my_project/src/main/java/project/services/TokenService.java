package project.services;

import project.models.User;

public interface TokenService {

    Long getUser(String token);
    String getToken(User user);

    String save(User model);
}
