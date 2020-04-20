package ru.kpfu.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
}
