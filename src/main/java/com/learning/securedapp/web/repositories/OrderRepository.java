package com.learning.securedapp.web.repositories;

import com.learning.securedapp.web.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * OrderRepository interface.
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public interface OrderRepository extends MongoRepository<Order, String> {}
