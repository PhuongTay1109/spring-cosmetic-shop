package com.cosmetics.myshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cosmetics.myshop.model.Product;
import com.cosmetics.myshop.repository.ProductRepository;
import com.cosmetics.myshop.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {	
	@Autowired
	ProductRepository productRepository;
	
	public List<Product> findAllProducts() {
		List<Product> products = productRepository.findAll();
		return products;
	}
	
	public List<Product> findProductsByCategoryName(String categoryName) {
		return productRepository.findByCategoryName(categoryName);
	}
	
	public List<Product> findProductsByCategoryPagination(String categoryName, Pageable pageable) {
	    return productRepository.findProductsByCategoryPagination(categoryName, pageable);
	}
	
	public Long countProductsByCategoryName(String categoryName) {
		return productRepository.countByCategoryName(categoryName);
	}
	
	public List<String> findBrandsByCategory(String categoryName) {
		return productRepository.findBrandsByCategory(categoryName);
	}
	
	public List<String> findProductTypesByCategory(String categoryName) {
		return productRepository.findProductTypesByCategory(categoryName);
	}
	
	public Product findProductByid(Integer id) {
		Optional<Product> product = productRepository.findById(id);
		if (product.isEmpty()) {
			return null;
		}
		return product.get();
	}
	public List<Product> findRelatedProductsByPage(Product product, Pageable pageable){
//		System.out.println("fetch data");
		return productRepository.findRelatedProductsByPage(product.getId().toString(), 
				product.getCategoryName(), 
				product.getBrand(), 
				product.getProductType(),
				pageable);
		
	}
	public long countRelatedProducts(Product product) {
		return productRepository.countRelatedProducts(product.getId().toString(),
				product.getCategoryName(), 
				product.getBrand(),
				product.getProductType());
	}
	
	public List <Product> searchProductsByKeyword(String keyword) {
		return productRepository.searchByKeyword(keyword);
	}
	
	public List<Product> findTopRatingProducts() {
		return productRepository.findTopRating();
	}
	
	public List<Product> findNewArrivalProducts() {
		return productRepository.findNewArrivals();
	}

	@Override
	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}
	
}
