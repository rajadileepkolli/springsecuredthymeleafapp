package com.learning.securedapp.web.services.impl;

import com.learning.securedapp.domain.Category;
import com.learning.securedapp.web.repositories.CategoryRepository;
import com.learning.securedapp.web.services.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

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
