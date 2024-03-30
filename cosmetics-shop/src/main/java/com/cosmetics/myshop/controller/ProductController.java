package com.cosmetics.myshop.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.cosmetics.myshop.model.Product;
import com.cosmetics.myshop.repository.ProductRepository;
import com.cosmetics.myshop.service.ProductService;
import com.cosmetics.myshop.utils.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
//@RequestMapping("/product")

public class ProductController {
	@Autowired
	ProductService productService;

	@GetMapping("/product/{product_id}")
	String getProduct(@PathVariable(name = "product_id") Integer id, Model model)
			throws JsonMappingException, JsonProcessingException {
		Product product = productService.findProductByid(id);
		ObjectMapper objectMapper = new ObjectMapper();
		List<String> tagList = new ArrayList<>();
		tagList = Arrays.asList(objectMapper.readValue(product.getTagList(), String[].class));
		StringUtils stringUtils = new StringUtils();
		long totalRelatedProducts = productService.countRelatedProducts(product);
//		System.out.println(totalRelatedProducts);
		model.addAttribute("product", product);
		model.addAttribute("tagList", tagList);
		model.addAttribute("product_id", id);
		model.addAttribute("stringUtils", stringUtils);
		model.addAttribute("totalRelatedProducts", totalRelatedProducts);
		return "/user/product_details";
	}

	@GetMapping("/products/{category_name}")
	String getProductsByCategory(@PathVariable(name = "category_name") String categoryName, Model model) {
		// Implement logic to retrieve products by category
		List<Product> products = productService.findProductsByCategoryName(categoryName);
		model.addAttribute("categoryName", categoryName);
		model.addAttribute("products", products);
		return "/user/products";
	}
}
