package com.learning.securedapp.web.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.learning.securedapp.domain.Category;

/**
 * <p>CategoryRepository interface.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public interface CategoryRepository extends MongoRepository<Category, String> {
	/**
	 * <p>findByCategoryName.</p>
	 *
	 * @param categoryName a {@link java.lang.String} object.
	 * @return a {@link com.learning.securedapp.domain.Category} object.
	 */
	Category findByCategoryName(String categoryName);
}
