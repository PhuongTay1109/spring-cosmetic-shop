package com.cosmetics.myshop.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cosmetics.myshop.model.Product;
import com.cosmetics.myshop.service.ProductService;

@Controller
@RequestMapping("/api")
public class ApiController {
	@Autowired
	ProductService productService;
	
	@ResponseBody
	@GetMapping("/related_products")
	List<Product> getRelatedProductsByPage(@RequestParam Map<String, String> param){
		Integer id = Integer.parseInt(param.get("id")) ;
		Integer page = Integer.parseInt(param.get("page"));
		Integer per_page = 12;
		Product product = productService.findProductByid(id);
		Pageable pageable = PageRequest.of(page,per_page);
		return productService.findRelatedProductsByPage(product, pageable);
	}
}
