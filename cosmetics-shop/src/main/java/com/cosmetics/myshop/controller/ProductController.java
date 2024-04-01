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
		List<String> brandList = productService.findBrandsByCategory(categoryName);
		List<String> typeList = productService.findProductTypesByCategory(categoryName);
		long totalProductsByCategory = productService.countProductsByCategoryName(categoryName);
		StringUtils stringUtils = new StringUtils();
		
		model.addAttribute("brandList", brandList);
		model.addAttribute("typeList", typeList);
		model.addAttribute("totalProductsByCategory", totalProductsByCategory);
		model.addAttribute("stringUtils", stringUtils);
		model.addAttribute("categoryName", categoryName);
		
		return "/user/products";
	}
}
