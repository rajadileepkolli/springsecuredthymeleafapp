package com.learning.securedapp.web.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderLines implements Serializable {
	private static final long serialVersionUID = 1L;

	final protected List<OrderLine> list;

	public OrderLines() {
		this(new ArrayList<>());
	}

	public OrderLines orderId(UUID orderId) {
		int i = 0;
		for (OrderLine orderLine : list) {
			orderLine.setLineNo(++i);
			orderLine.setOrderId(orderId);
		}
		return this;
	}

	public Stream<OrderLine> stream() {
		return list.stream();
	}

	public double getTotal() {
		return list.stream().mapToDouble(OrderLine::getSubtotal).sum();
	}
}
