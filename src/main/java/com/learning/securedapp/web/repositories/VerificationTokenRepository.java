package com.learning.securedapp.web.repositories;

import com.learning.securedapp.domain.VerificationToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends MongoRepository<VerificationToken, String> {

    /**
     * findByToken.
     *
     * @param token a {@link java.lang.String} object.
     * @return a {@link com.learning.securedapp.domain.VerificationToken} object.
     */
    VerificationToken findByToken(String token);
}
