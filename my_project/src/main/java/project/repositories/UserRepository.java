package project.repositories;

import project.models.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Long, User> {
    void verification(Long id);
    Optional<User> findUserByEmail(String email);
    boolean isVerified(Long id);
}
