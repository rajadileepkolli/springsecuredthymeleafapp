package com.learning.securedapp.web.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.learning.securedapp.domain.VerificationToken;

/**
 * <p>VerificationTokenRepository interface.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public interface VerificationTokenRepository extends MongoRepository<VerificationToken, String> {

	/**
	 * <p>findByToken.</p>
	 *
	 * @param token a {@link java.lang.String} object.
	 * @return a {@link com.learning.securedapp.domain.VerificationToken} object.
	 */
	VerificationToken findByToken(String token);

	/**
	 * <p>findById.</p>
	 *
	 * @param id a {@link java.lang.String} object.
	 * @return a {@link com.learning.securedapp.domain.VerificationToken} object.
	 */
	VerificationToken findById(String id);
}
