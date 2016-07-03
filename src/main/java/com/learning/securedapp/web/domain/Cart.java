package com.learning.securedapp.web.domain;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Cart implements Serializable{


    private static final long serialVersionUID = 1L;
    private final OrderLines orderLines;

    public Cart() {
        this(new OrderLines());
    }

    public Cart add(OrderLine orderLine) {
        // Check whether the target of the product is already in the shopping basket
        Optional<OrderLine> opt = orderLines.stream().filter(x ->
                Objects.equals(x.getGoods().getProductId(), orderLine.getGoods().getProductId()))
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

    public Cart clear() {
        orderLines.list.clear();
        return this;
    }

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

    public boolean isEmpty() {
        return orderLines.list.isEmpty();
    }

}
