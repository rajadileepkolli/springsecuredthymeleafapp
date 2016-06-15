package com.learning.securedapp.web.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.learning.securedapp.domain.Product;

public interface ProductRepository extends MongoRepository<Product, String>{
}
