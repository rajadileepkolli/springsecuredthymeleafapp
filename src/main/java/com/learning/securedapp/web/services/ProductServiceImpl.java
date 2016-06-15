package com.learning.securedapp.web.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.securedapp.domain.Product;
import com.learning.securedapp.exception.SecuredAppException;
import com.learning.securedapp.web.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
    
    @Autowired private ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(String id) {
        return productRepository.findOne(id);
    }

    @Override
    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.delete(id);
    }

    @Override
    public Product updateProduct(Product product) throws SecuredAppException {
        Product persistedProduct = getProductById(product.getProductId());
        if(persistedProduct == null){
            throw new SecuredAppException("Product "+product.getProductId()+" doesn't exist");
        }
        return productRepository.save(persistedProduct);
    }

}
