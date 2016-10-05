package com.learning.securedapp.web.services;

import java.util.List;

import com.learning.securedapp.domain.Product;

/**
 * <p>ProductService interface.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public interface ProductService {

	/**
	 * <p>saveProduct.</p>
	 *
	 * @param product a {@link com.learning.securedapp.domain.Product} object.
	 * @return a {@link com.learning.securedapp.domain.Product} object.
	 */
	Product saveProduct(Product product);

	/**
	 * <p>getProductById.</p>
	 *
	 * @param id a {@link java.lang.String} object.
	 * @return a {@link com.learning.securedapp.domain.Product} object.
	 */
	Product getProductById(String id);

	/**
	 * <p>listAllProducts.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	List<Product> listAllProducts();

	/**
	 * <p>deleteProduct.</p>
	 *
	 * @param id a {@link java.lang.String} object.
	 */
	void deleteProduct(String id);

}
