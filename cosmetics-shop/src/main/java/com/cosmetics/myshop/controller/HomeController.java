package com.cosmetics.myshop.controller;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
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
		List<Product> productList = productService.searchProductsByKeyword(keyword);
		
		List<String> categoryNames = categoryService.findAllCategories().stream()
				.map(Category::getCategoryName)
				.collect(Collectors.toList());
		model.addAttribute("productList", productList);
		model.addAttribute("categoryNames", categoryNames);
		model.addAttribute("keyword", keyword);
		return "user/search";
	}
	

	@GetMapping("/")
	private String home(Model model) {
	
		List<Product> topRatingProducts = productService.findTopRatingProducts();
		List<Product> newArrivalProducts = productService.findNewArrivalProducts();
		model.addAttribute("topRatingProducts", topRatingProducts);
		model.addAttribute("newArrivalProducts", newArrivalProducts);
		
		return "/user/homepage";
	}

}
