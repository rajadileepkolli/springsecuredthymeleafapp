package com.learning.securedapp.web.services.impl;

import com.learning.securedapp.exception.EmptyCartOrderException;
import com.learning.securedapp.exception.InvalidCartOrderException;
import com.learning.securedapp.web.domain.Cart;
import com.learning.securedapp.web.domain.Order;
import com.learning.securedapp.web.domain.OrderLines;
import com.learning.securedapp.web.repositories.OrderRepository;
import com.learning.securedapp.web.services.OrderService;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    /** {@inheritDoc} */
    @Override
    public Object calcSignature(Cart cart) {
        byte[] serialized = SerializationUtils.serialize(cart.getOrderLines());
        byte[] signature = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            signature = messageDigest.digest(serialized);
        } catch (NoSuchAlgorithmException ignored) {
        }
        return Base64.getEncoder().encodeToString(signature);
    }

    /** {@inheritDoc} */
    @Override
    public Order purchase(String email, Cart cart, String signature) {
        if (cart.isEmpty()) {
            throw new EmptyCartOrderException("Shopping basket is empty");
        }
        if (!Objects.equals(calcSignature(cart), signature)) {
            throw new InvalidCartOrderException("It has changed the state of the shopping basket");
        }

        // In order to be removed from the shopping basket , keep deep copy
        OrderLines orderLines =
                (OrderLines)
                        SerializationUtils.deserialize(
                                SerializationUtils.serialize(cart.getOrderLines()));

        Order order =
                Order.builder()
                        .email(email)
                        .orderDate(LocalDate.now())
                        .orderLines(orderLines)
                        .build();
        Order ordered = orderRepository.save(createFullOrder(order));
        cart.clear();
        return ordered;
    }

    private Order createFullOrder(Order order) {
        UUID orderId = UUID.randomUUID();
        order.setOrderId(orderId);
        order.getOrderLines().orderId(orderId);

        return order;
    }
}
