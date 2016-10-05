package com.learning.securedapp.web.services;

import com.learning.securedapp.web.domain.Cart;
import com.learning.securedapp.web.domain.Order;

/**
 * <p>OrderService interface.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public interface OrderService {

	/**
	 * <p>calcSignature.</p>
	 *
	 * @param cart a {@link com.learning.securedapp.web.domain.Cart} object.
	 * @return a {@link java.lang.Object} object.
	 */
	Object calcSignature(Cart cart);

	/**
	 * <p>purchase.</p>
	 *
	 * @param account a {@link java.lang.String} object.
	 * @param cart a {@link com.learning.securedapp.web.domain.Cart} object.
	 * @param signature a {@link java.lang.String} object.
	 * @return a {@link com.learning.securedapp.web.domain.Order} object.
	 */
	Order purchase(String account, Cart cart, String signature);

}
