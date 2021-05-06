package com.learning.securedapp.web.services;

import com.learning.securedapp.domain.Category;
import java.util.List;

/**
 * CategoryService interface.
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public interface CategoryService {

    /**
     * saveCategory.
     *
     * @param category a {@link com.learning.securedapp.domain.Category} object.
     * @return a {@link com.learning.securedapp.domain.Category} object.
     */
    public Category saveCategory(Category category);

    /**
     * getAllCategories.
     *
     * @return a {@link java.util.List} object.
     */
    public List<Category> getAllCategories();

    /**
     * getbyName.
     *
     * @param categoryName a {@link java.lang.String} object.
     * @return a {@link com.learning.securedapp.domain.Category} object.
     */
    public Category getbyName(String categoryName);
}
