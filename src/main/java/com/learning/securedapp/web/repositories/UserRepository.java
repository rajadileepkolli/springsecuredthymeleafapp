package com.learning.securedapp.web.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.learning.securedapp.domain.User;

public interface UserRepository extends MongoRepository<User, String>{

    User findByUserName(String username);
    User findByEmail(String email);
    
}
