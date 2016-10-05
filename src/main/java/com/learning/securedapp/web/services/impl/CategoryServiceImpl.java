package com.learning.securedapp.web.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.securedapp.domain.Category;
import com.learning.securedapp.web.repositories.CategoryRepository;
import com.learning.securedapp.web.services.CategoryService;

@Service
/**
 * <p>CategoryServiceImpl class.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

	/** {@inheritDoc} */
	@Override
	public Category saveCategory(Category category) {
		return categoryRepository.save(category);
	}

	/** {@inheritDoc} */
	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	/** {@inheritDoc} */
	@Override
	public Category getbyName(String categoryName) {
		return categoryRepository.findByCategoryName(categoryName);
	}

}
