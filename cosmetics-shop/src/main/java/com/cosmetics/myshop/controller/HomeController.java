package com.cosmetics.myshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cosmetics.myshop.model.Category;
import com.cosmetics.myshop.service.CategoryService;
import com.cosmetics.myshop.utils.StringUtils;

@Controller
public class HomeController {

	@Autowired
	CategoryService categoryService;

	@GetMapping("/register")
	private String register() {
		return "security/register";
	}

	@GetMapping("/login")
	private String login() {
		System.out.println("Login page");
		return "/security/login";
	}

	@GetMapping("/")
	private String home(Model model) {
		List<Category> categoryList = categoryService.findAllCategories();
		StringUtils stringUtils = new StringUtils();
		model.addAttribute("stringUtils", stringUtils);
		model.addAttribute("categoryList", categoryList);
		return "/user/homepage";
	}

}
