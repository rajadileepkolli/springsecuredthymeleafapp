package com.learning.securedapp.web.services.impl;

import com.learning.securedapp.domain.Product;
import com.learning.securedapp.web.repositories.ProductRepository;
import com.learning.securedapp.web.services.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    /** {@inheritDoc} */
    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    /** {@inheritDoc} */
    @Override
    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    /** {@inheritDoc} */
    @Override
    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    /** {@inheritDoc} */
    @Override
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}
