package com.learning.securedapp.web.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.learning.securedapp.domain.RememberMeToken;

public interface RememberMeTokenRepository extends MongoRepository<RememberMeToken, String>{
    RememberMeToken findBySeries(String series);
    List<RememberMeToken> findByUsername(String username);
}
