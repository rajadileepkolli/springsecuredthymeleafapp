package com.learning.securedapp.web.domain;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Cart class.
 *
 * @author rajakolli
 * @version $Id: $Id
 */
@Data
@AllArgsConstructor
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;
    private final OrderLines orderLines;

    /** Constructor for Cart. */
    public Cart() {
        this(new OrderLines());
    }

    /**
     * add.
     *
     * @param orderLine a {@link com.learning.securedapp.web.domain.OrderLine} object.
     * @return a {@link com.learning.securedapp.web.domain.Cart} object.
     */
    public Cart add(OrderLine orderLine) {
        // Check whether the target of the product is already in the shopping
        // basket
        Optional<OrderLine> opt =
                orderLines.stream()
                        .filter(
                                x ->
                                        Objects.equals(
                                                x.getGoods().getProductId(),
                                                orderLine.getGoods().getProductId()))
                        .findFirst();
        if (opt.isPresent()) {
            // Increasing the quantity when I entered
            OrderLine line = opt.get();
            line.setQuantity(line.getQuantity() + orderLine.getQuantity());
        } else {
            orderLines.list.add(orderLine);
        }
        return this;
    }

    /**
     * clear.
     *
     * @return a {@link com.learning.securedapp.web.domain.Cart} object.
     */
    public Cart clear() {
        orderLines.list.clear();
        return this;
    }

    /**
     * remove.
     *
     * @param lineNo a {@link java.util.Set} object.
     * @return a {@link com.learning.securedapp.web.domain.Cart} object.
     */
    public Cart remove(Set<Integer> lineNo) {
        Iterator<OrderLine> iterator = getOrderLines().getList().iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            iterator.next();
            if (lineNo.contains(i)) {
                iterator.remove();
            }
        }
        return this;
    }

    /**
     * isEmpty.
     *
     * @return a boolean.
     */
    public boolean isEmpty() {
        return orderLines.list.isEmpty();
    }
}
