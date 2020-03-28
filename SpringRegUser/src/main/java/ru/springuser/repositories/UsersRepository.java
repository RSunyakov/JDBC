package ru.springuser.repositories;

import ru.springuser.model.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<Long, User> {
    Optional<User> findByCode(String code);
    void updateCode(Long id, String code);
    Optional<User> findByEmail(String email);
}
