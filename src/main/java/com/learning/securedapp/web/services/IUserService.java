package com.learning.securedapp.web.services;

import com.learning.securedapp.domain.User;

/**
 * IUserService interface.
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public interface IUserService {

    /**
     * createVerificationTokenForUser.
     *
     * @param user a {@link com.learning.securedapp.domain.User} object.
     * @param token a {@link java.lang.String} object.
     */
    void createVerificationTokenForUser(User user, String token);

    /**
     * validateVerificationToken.
     *
     * @param token a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    String validateVerificationToken(String token);
}
