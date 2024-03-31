package com.cosmetics.myshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cosmetics.myshop.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByCategoryName(String categoryName);
	Optional<Product> findById(Integer id);
	
	@Query("SELECT p FROM Product p where p.id != ?1 and (p.categoryName = ?2 or p.brand = ?3 or p.productType=?4)")
	List<Product> findRelatedProductsByPage(String id, String categoryName, String brand, String productType, Pageable pageable);
	
	@Query("SELECT Count(p) FROM Product p where p.id != ?1 and (p.categoryName = ?2 or p.brand = ?3 or p.productType=?4)")
	long countRelatedProducts(String id, String categoryName, String brand, String productType);
	
	@Query("SELECT p FROM Product p WHERE p.categoryName = :categoryName")
	List<Product> findProductsByCategoryPagination(String categoryName, Pageable pageable);
	
	long countByCategoryName(String categoryName);
	
	@Query("SELECT DISTINCT p.brand FROM Product p WHERE p.categoryName = :categoryName and p.brand IS NOT NULL")
	List<String> findBrandsByCategory(String categoryName);
	
	@Query("SELECT DISTINCT p.productType FROM Product p WHERE p.categoryName = :categoryName and p.productType IS NOT NULL")
	List<String> findProductTypesByCategory(String categoryName);
}
