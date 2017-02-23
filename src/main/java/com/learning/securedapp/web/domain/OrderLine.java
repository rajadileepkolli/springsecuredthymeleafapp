package com.learning.securedapp.web.domain;

import java.io.Serializable;
import java.util.UUID;

import com.learning.securedapp.domain.Product;

import lombok.Builder;
import lombok.Data;

@Data
/**
 * <p>OrderLine class.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
@Builder
public class OrderLine implements Serializable {

	private static final long serialVersionUID = 1L;

	private Product goods;
	private int quantity;
	private UUID orderId;
	private int lineNo;

	/**
	 * <p>getSubtotal.</p>
	 *
	 * @return a double.
	 */
	public double getSubtotal() {
		return quantity * goods.getPrice();
	}
}
