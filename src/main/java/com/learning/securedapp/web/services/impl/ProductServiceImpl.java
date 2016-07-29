package com.learning.securedapp.web.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.securedapp.domain.Product;
import com.learning.securedapp.web.repositories.ProductRepository;
import com.learning.securedapp.web.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

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

}
