package com.learning.securedapp.web.services;

import com.learning.securedapp.domain.Product;
import java.util.List;

/**
 * ProductService interface.
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public interface ProductService {

    /**
     * saveProduct.
     *
     * @param product a {@link com.learning.securedapp.domain.Product} object.
     * @return a {@link com.learning.securedapp.domain.Product} object.
     */
    Product saveProduct(Product product);

    /**
     * getProductById.
     *
     * @param id a {@link java.lang.String} object.
     * @return a {@link com.learning.securedapp.domain.Product} object.
     */
    Product getProductById(String id);

    /**
     * listAllProducts.
     *
     * @return a {@link java.util.List} object.
     */
    List<Product> listAllProducts();

    /**
     * deleteProduct.
     *
     * @param id a {@link java.lang.String} object.
     */
    void deleteProduct(String id);
}
