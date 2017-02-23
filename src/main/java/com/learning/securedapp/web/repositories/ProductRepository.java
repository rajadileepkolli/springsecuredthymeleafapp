package com.learning.securedapp.web.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.learning.securedapp.domain.Product;

/**
 * <p>ProductRepository interface.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public interface ProductRepository extends MongoRepository<Product, String> {
}
