package com.cosmetics.myshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosmetics.myshop.model.Product;
import com.cosmetics.myshop.repository.ProductRepository;

@Service
public class ProductService {
	
//	public static void main(String[] args) {
//		List<Product> list = productRepository.findAll();
//		for (Product p : list ) {
//			System.out.println(p.toString());
//		}
//	}
	
	@Autowired
	ProductRepository productRepository;
	
	public List<Product> findAllProducts() {
		return productRepository.findAll();
	}
	
	public List<Product> findProductsByCategoryName(String categoryName) {
		return productRepository.findByCategoryName(categoryName);
	}
	
	public Product findProductByid(Integer id) {
		Optional<Product> product = productRepository.findById(id);
		if (product.isEmpty()) {
			return null;
		}
		return product.get();
	}

}
