package project.repositories;

import project.models.VerificationToken;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<Long, VerificationToken> {
    Optional<VerificationToken> findByToken(String token);
}
