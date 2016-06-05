package com.learning.securedapp.web.services;

import com.learning.securedapp.domain.User;

public interface IUserService {

    void createVerificationTokenForUser(User user, String token);

    String validateVerificationToken(String token);

}
