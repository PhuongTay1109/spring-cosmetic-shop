package com.cosmetics.myshop.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cosmetics.myshop.model.Category;
import com.cosmetics.myshop.model.Product;
import com.cosmetics.myshop.model.User;
import com.cosmetics.myshop.service.CategoryService;
import com.cosmetics.myshop.service.ProductService;
import com.cosmetics.myshop.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	UserService userService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;
	
	@GetMapping("/dashboard")
	public String getAdminDashboard() {
		return "admin/dashboard";
	}
	@GetMapping("/users")
	public String getUsers(Model model) {
		List<User> userList = userService.findAllUsers();
		model.addAttribute("userList", userList);
		return "admin/users";
	}
	@GetMapping("/categories")
	public String getCategories(Model model) {
		List<Category> categoryList = categoryService.findAllCategories();
		model.addAttribute("categoryList", categoryList);
		return "admin/categories";
	}
	@GetMapping("/products/{categoryName}")
	public String getProducts(@PathVariable(name = "categoryName") String categoryName, Model model) {
		List<String> brandList = productService.findBrandsByCategory(categoryName);
		List<String> typeList = productService.findProductTypesByCategory(categoryName);
		long totalProductsByCategory = productService.countProductsByCategoryName(categoryName);
		model.addAttribute("brandList", brandList);
		model.addAttribute("typeList", typeList);
		model.addAttribute("totalProductsByCategory", totalProductsByCategory);
		model.addAttribute("categoryName", categoryName);
		return "admin/products";
	}
	
	@GetMapping("/profile")
	public String getProfile(Authentication authentication, Model model) {
		User user = (User)authentication.getPrincipal();
		model.addAttribute("user", user);
		return "admin/profile";
	}
	@ResponseBody
	@PutMapping("/profile") 
	public Map<String, Object> processUpdateProfile(@RequestBody Map<String, String> body) {
		return userService.updateProfile(body); //Pass @RequsetBody, return Response with Map
	}
	
	@GetMapping("/profile/password/change")
	public String getProfilePasswordChange(Authentication authentication, Model model) {
		return "admin/password_change";
	}
	@ResponseBody
	@PutMapping("/profile/password/change")
	public Map<String, Object> processPasswordChange(@RequestBody Map<String, String> body, Authentication authentication) {
		//TODO: process PUT request
		User user = (User)authentication.getPrincipal();
		return userService.changePassword(body, user);
	}
	
	
}
