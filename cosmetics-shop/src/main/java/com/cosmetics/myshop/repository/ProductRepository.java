package com.cosmetics.myshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cosmetics.myshop.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByCategoryName(String categoryName);
}
