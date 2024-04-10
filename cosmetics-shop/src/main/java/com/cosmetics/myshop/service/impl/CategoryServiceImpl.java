package com.cosmetics.myshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosmetics.myshop.model.Category;
import com.cosmetics.myshop.repository.CategoryRepository;
import com.cosmetics.myshop.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryRepository categoryRepository;
	
	public List<Category> findAllCategories() {
		return categoryRepository.findAll(); 
	}
}
