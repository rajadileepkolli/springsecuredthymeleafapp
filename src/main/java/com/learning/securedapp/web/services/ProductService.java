package com.learning.securedapp.web.services;

import java.util.List;

import com.learning.securedapp.domain.Product;
import com.learning.securedapp.exception.SecuredAppException;

public interface ProductService {

    Product saveProduct(Product product);

    Product getProductById(String id);

    List<Product> listAllProducts();
    
    void deleteProduct(String id);

    Product updateProduct(Product product) throws SecuredAppException;

}
