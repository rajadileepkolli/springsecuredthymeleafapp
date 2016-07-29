package com.learning.securedapp.web.services;

import java.util.List;

import com.learning.securedapp.domain.Category;

public interface CategoryService {

	public Category saveCategory(Category category);

	public List<Category> getAllCategories();

	public Category getbyName(String categoryName);

}
