package com.learning.securedapp.web.repositories;

import com.learning.securedapp.domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * ProductRepository interface.
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public interface ProductRepository extends MongoRepository<Product, String> {}
