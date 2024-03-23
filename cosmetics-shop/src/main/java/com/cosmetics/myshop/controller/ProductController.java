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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/product")

public class ProductController {
	@Autowired
	ProductService productService;
	
	@GetMapping("/{product_id}")
	String getProduct(@PathVariable(name = "product_id") Integer id, Model model) throws JsonMappingException, JsonProcessingException {
		Product product = productService.findProductByid(id);
//		System.out.println(product);
//		System.out.println(product.getTagList());
		model.addAttribute("product", product);
		
		ObjectMapper objectMapper = new ObjectMapper();
		List<String> tagList = new ArrayList<>();
		tagList = Arrays.asList(objectMapper.readValue(product.getTagList(), String[].class));
		model.addAttribute("tagList", tagList);
		model.addAttribute("product_id", id);
		return "/user/product_details";
	}
	
}
