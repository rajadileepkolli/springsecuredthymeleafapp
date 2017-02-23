package com.learning.securedapp.web.services;

import com.learning.securedapp.domain.User;

/**
 * <p>IUserService interface.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public interface IUserService {

	/**
	 * <p>createVerificationTokenForUser.</p>
	 *
	 * @param user a {@link com.learning.securedapp.domain.User} object.
	 * @param token a {@link java.lang.String} object.
	 */
	void createVerificationTokenForUser(User user, String token);

	/**
	 * <p>validateVerificationToken.</p>
	 *
	 * @param token a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	String validateVerificationToken(String token);

}
