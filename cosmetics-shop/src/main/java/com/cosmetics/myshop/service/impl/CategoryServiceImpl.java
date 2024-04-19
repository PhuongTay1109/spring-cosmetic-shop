package com.cosmetics.myshop.service.impl;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosmetics.myshop.model.Category;
import com.cosmetics.myshop.model.Const;
import com.cosmetics.myshop.model.Product;
import com.cosmetics.myshop.repository.CategoryRepository;
import com.cosmetics.myshop.repository.ProductRepository;
import com.cosmetics.myshop.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	ProductRepository productRepository;
	
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

	@Override
	public Category updateCategory(Category category, String oldCategoryName, boolean deleteOldAvatar) throws Exception {
		Date date = new Date();
		List<Product> productList = productRepository.findByCategoryName(oldCategoryName);
		for (Product product : productList) {
			product.setCategoryName(category.getCategoryName());
			productRepository.save(product);
		}	
		Category oldCategory = categoryRepository.findByCategoryName(oldCategoryName).get();
		if (!deleteOldAvatar) {
			category.setImageLink(oldCategory.getImageLink());
			System.out.println("change avatar " + category.getImageLink());
		} else Files.deleteIfExists(Paths.get(Const.IMAGE_UPLOAD_DIRECTORY, oldCategory.getImageLink()));
		category.setCreatedAt(oldCategory.getCreatedAt());
		category.setModifiedAt(date);
		categoryRepository.delete(oldCategory);
		return categoryRepository.save(category);
	}


}
