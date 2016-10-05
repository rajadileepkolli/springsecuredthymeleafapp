package com.learning.securedapp.web.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.learning.securedapp.web.domain.Order;

/**
 * <p>OrderRepository interface.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public interface OrderRepository extends MongoRepository<Order, String> {

}
