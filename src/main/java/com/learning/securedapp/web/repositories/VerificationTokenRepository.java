package com.learning.securedapp.web.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.learning.securedapp.domain.VerificationToken;

public interface VerificationTokenRepository extends MongoRepository<VerificationToken, String>{

    VerificationToken findByToken(String token);
    
    VerificationToken findById(String id);   
}
