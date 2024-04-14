package com.cosmetics.myshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cosmetics.myshop.model.Category;
import com.cosmetics.myshop.model.User;
import com.cosmetics.myshop.service.CategoryService;
import com.cosmetics.myshop.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	UserService userService;
	@Autowired
	CategoryService categoryService;
	
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
}
