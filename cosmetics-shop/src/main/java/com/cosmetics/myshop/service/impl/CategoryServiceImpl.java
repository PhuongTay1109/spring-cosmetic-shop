package com.cosmetics.myshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosmetics.myshop.model.Category;
import com.cosmetics.myshop.model.Product;
import com.cosmetics.myshop.repository.CategoryRepository;
import com.cosmetics.myshop.repository.ProductRepository;
import com.cosmetics.myshop.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryRepository categoryRepository;
	
	public List<Category> findAllCategories() {
		return categoryRepository.findAll(); 
	}

	@Override
	public Category saveCategory(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public Optional<Category> findCategoryByName(String categoryName) {
		return categoryRepository.findByCategoryName(categoryName);
	}

	@Override
	public void deleteCategory(Category category) {
		categoryRepository.delete(category);
	}


}
