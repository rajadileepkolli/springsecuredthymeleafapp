package com.learning.securedapp.web.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.learning.securedapp.domain.RememberMeToken;

/**
 * <p>RememberMeTokenRepository interface.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public interface RememberMeTokenRepository extends MongoRepository<RememberMeToken, String> {
	/**
	 * <p>findBySeries.</p>
	 *
	 * @param series a {@link java.lang.String} object.
	 * @return a {@link com.learning.securedapp.domain.RememberMeToken} object.
	 */
	RememberMeToken findBySeries(String series);

	/**
	 * <p>findByUsername.</p>
	 *
	 * @param username a {@link java.lang.String} object.
	 * @return a {@link java.util.List} object.
	 */
	List<RememberMeToken> findByUsername(String username);
}
