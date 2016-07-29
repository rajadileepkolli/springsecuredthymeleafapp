package com.learning.securedapp.web.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.learning.securedapp.domain.Category;

public interface CategoryRepository extends MongoRepository<Category, String> {
	Category findByCategoryName(String categoryName);
}
