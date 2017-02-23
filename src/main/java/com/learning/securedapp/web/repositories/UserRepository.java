package com.learning.securedapp.web.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.learning.securedapp.domain.User;

/**
 * <p>UserRepository interface.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public interface UserRepository extends MongoRepository<User, String> {

	/**
	 * <p>findByUserName.</p>
	 *
	 * @param username a {@link java.lang.String} object.
	 * @return a {@link com.learning.securedapp.domain.User} object.
	 */
	User findByUserName(String username);

	/**
	 * <p>findByEmail.</p>
	 *
	 * @param email a {@link java.lang.String} object.
	 * @return a {@link com.learning.securedapp.domain.User} object.
	 */
	User findByEmail(String email);

}
