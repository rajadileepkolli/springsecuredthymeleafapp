package com.learning.securedapp.web.services;

import java.util.List;

import com.learning.securedapp.domain.Category;

/**
 * <p>CategoryService interface.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public interface CategoryService {

	/**
	 * <p>saveCategory.</p>
	 *
	 * @param category a {@link com.learning.securedapp.domain.Category} object.
	 * @return a {@link com.learning.securedapp.domain.Category} object.
	 */
	public Category saveCategory(Category category);

	/**
	 * <p>getAllCategories.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<Category> getAllCategories();

	/**
	 * <p>getbyName.</p>
	 *
	 * @param categoryName a {@link java.lang.String} object.
	 * @return a {@link com.learning.securedapp.domain.Category} object.
	 */
	public Category getbyName(String categoryName);

}
