package com.cosmetics.myshop.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cosmetics.myshop.model.Category;
import com.cosmetics.myshop.model.Product;
import com.cosmetics.myshop.repository.CategoryRepository;
import com.cosmetics.myshop.repository.ProductRepository;
import com.cosmetics.myshop.service.CategoryService;
import com.cosmetics.myshop.service.ProductService;

@Controller
public class TestController {
	@Autowired
	ProductService productService;
	
	@Autowired
	CategoryService categoryService;
	
	@ResponseBody
	@GetMapping("/categories")
	public List<Category> getCategories() { 
		return categoryService.findAllCategories();
	}
	@ResponseBody
	@GetMapping("/products")
	public List<Product> getProducts() { 
		return productService.findAllProducts();
	}
	
	@ResponseBody
	@GetMapping("/user")
	public Authentication getaAuthentication(Authentication authentication) {
		return authentication;
	}
	
	
	
}
