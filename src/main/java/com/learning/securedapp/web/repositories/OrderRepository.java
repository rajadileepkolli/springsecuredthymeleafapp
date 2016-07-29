package com.learning.securedapp.web.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.learning.securedapp.web.domain.Order;

public interface OrderRepository extends MongoRepository<Order, String> {

}
