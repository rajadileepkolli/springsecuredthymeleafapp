package com.learning.securedapp.web.repositories;

import com.learning.securedapp.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    /**
     * findByCategoryName.
     *
     * @param categoryName a {@link java.lang.String} object.
     * @return a {@link com.learning.securedapp.domain.Category} object.
     */
    Category findByCategoryName(String categoryName);
}
