package com.cosmetics.myshop.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cosmetics.myshop.model.Category;
import com.cosmetics.myshop.model.Product;
import com.cosmetics.myshop.service.CategoryService;
import com.cosmetics.myshop.service.ProductService;
import com.cosmetics.myshop.utils.StringUtils;

@Controller
public class HomeController {

	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;

	@GetMapping("/search")
	private String search(@RequestParam String keyword, Model model) {
		System.out.println(keyword);
		List<Product> productList = productService.searchProductsByKeyword(keyword);
		
		List<String> categoryList = categoryService.findAllCategories().stream()
				.map(Category::getCategoryName)
				.collect(Collectors.toList());
		model.addAttribute("productList", productList);
		model.addAttribute("keyword", keyword);
		System.out.println(productList.toString());
		return "user/search";
	}

	@GetMapping("/register")
	private String register() {
		return "security/register";
	}

	@GetMapping("/login")
	private String login() {
		System.out.println("Login page");
		return "security/login";
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
