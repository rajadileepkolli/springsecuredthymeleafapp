package com.learning.securedapp.web.services;

import com.learning.securedapp.web.domain.Cart;
import com.learning.securedapp.web.domain.Order;

/**
 * OrderService interface.
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public interface OrderService {

    /**
     * calcSignature.
     *
     * @param cart a {@link com.learning.securedapp.web.domain.Cart} object.
     * @return a {@link java.lang.Object} object.
     */
    Object calcSignature(Cart cart);

    /**
     * purchase.
     *
     * @param account a {@link java.lang.String} object.
     * @param cart a {@link com.learning.securedapp.web.domain.Cart} object.
     * @param signature a {@link java.lang.String} object.
     * @return a {@link com.learning.securedapp.web.domain.Order} object.
     */
    Order purchase(String account, Cart cart, String signature);
}
