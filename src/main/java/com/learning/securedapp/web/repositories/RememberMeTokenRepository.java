package com.learning.securedapp.web.repositories;

import com.learning.securedapp.domain.RememberMeToken;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * RememberMeTokenRepository interface.
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public interface RememberMeTokenRepository extends MongoRepository<RememberMeToken, String> {
    /**
     * findBySeries.
     *
     * @param series a {@link java.lang.String} object.
     * @return a {@link com.learning.securedapp.domain.RememberMeToken} object.
     */
    RememberMeToken findBySeries(String series);

    /**
     * findByUsername.
     *
     * @param username a {@link java.lang.String} object.
     * @return a {@link java.util.List} object.
     */
    List<RememberMeToken> findByUsername(String username);
}
