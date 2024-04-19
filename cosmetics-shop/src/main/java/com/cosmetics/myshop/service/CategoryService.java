package com.cosmetics.myshop.service;

import java.util.List;
import java.util.Optional;

import com.cosmetics.myshop.model.Category;


public interface CategoryService {
	public List<Category> findAllCategories();
	public Category saveCategory(Category category);
	public Optional<Category> findCategoryByName(String categoryName);
	public void deleteCategory(Category category);
	public Category updateCategory(Category category, String oldCategoryName, boolean deleteOldAvatar) throws Exception;
}
