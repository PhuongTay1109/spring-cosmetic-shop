package com.cosmetics.myshop.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.cosmetics.myshop.model.Product;

public interface ProductService {	

	public List<Product> findAllProducts();
	public List<Product> findProductsByCategoryName(String categoryName);
	public List<Product> findProductsByCategoryPagination(String categoryName, Pageable pageable);
	public Long countProductsByCategoryName(String categoryName);
	public List<String> findBrandsByCategory(String categoryName);
	public List<String> findProductTypesByCategory(String categoryName);
	public Product findProductByid(Integer id);
	public List<Product> findRelatedProductsByPage(Product product, Pageable pageable);
	public long countRelatedProducts(Product product);
	public List <Product> searchProductsByKeyword(String keyword);
	public List<Product> findTopRatingProducts();
	public List<Product> findNewArrivalProducts();
}
