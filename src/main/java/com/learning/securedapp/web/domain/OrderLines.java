package com.learning.securedapp.web.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
/**
 * <p>OrderLines class.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
@AllArgsConstructor
public class OrderLines implements Serializable {
	private static final long serialVersionUID = 1L;

	final protected List<OrderLine> list;

	/**
	 * <p>Constructor for OrderLines.</p>
	 */
	public OrderLines() {
		this(new ArrayList<>());
	}

	/**
	 * <p>orderId.</p>
	 *
	 * @param orderId a {@link java.util.UUID} object.
	 * @return a {@link com.learning.securedapp.web.domain.OrderLines} object.
	 */
	public OrderLines orderId(UUID orderId) {
		int i = 0;
		for (OrderLine orderLine : list) {
			orderLine.setLineNo(++i);
			orderLine.setOrderId(orderId);
		}
		return this;
	}

	/**
	 * <p>stream.</p>
	 *
	 * @return a {@link java.util.stream.Stream} object.
	 */
	public Stream<OrderLine> stream() {
		return list.stream();
	}

	/**
	 * <p>getTotal.</p>
	 *
	 * @return a double.
	 */
	public double getTotal() {
		return list.stream().mapToDouble(OrderLine::getSubtotal).sum();
	}
}
