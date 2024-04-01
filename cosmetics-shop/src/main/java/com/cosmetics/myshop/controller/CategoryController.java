package com.cosmetics.myshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cosmetics.myshop.model.Category;
import com.cosmetics.myshop.service.CategoryService;

@RequestMapping("/category")
@Controller
public class CategoryController {
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("/all")
	public String getAllCategories(Model model) {
		List<Category> categoryList = categoryService.findAllCategories();
		model.addAttribute("categoryList", categoryList);
		return "user/category_all";
	}
	
}
