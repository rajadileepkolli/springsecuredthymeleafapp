package com.learning.securedapp.web.services;

import com.learning.securedapp.web.domain.Cart;
import com.learning.securedapp.web.domain.Order;

public interface OrderService {

    Object calcSignature(Cart cart);

    Order purchase(String account, Cart cart, String signature);

}
