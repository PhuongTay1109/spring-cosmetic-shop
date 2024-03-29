package com.cosmetics.myshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cosmetics.myshop.model.Category;
import com.cosmetics.myshop.service.CategoryService;
import com.cosmetics.myshop.utils.StringUtils;

@ControllerAdvice(annotations = Controller.class)
public class GlobalControllerAdvice {
	@Autowired
	CategoryService categoryService;

	@ModelAttribute("categoryList")
	public List<Category> getCategoryList() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			return categoryService.findAllCategories();
		}
		return null;
	}
	@ModelAttribute("stringUtils")
	public StringUtils getStringUtils() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			StringUtils stringUtils = new StringUtils();
			return stringUtils;
		}
		return null;
	}

}
