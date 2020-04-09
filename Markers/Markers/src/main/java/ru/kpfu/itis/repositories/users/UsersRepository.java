package ru.kpfu.itis.repositories.users;

import ru.kpfu.itis.models.User;
import ru.kpfu.itis.repositories.CrudRepository;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<Long, User> {
    Optional<User> findByEmail(String email);
    void update(User user);
}
