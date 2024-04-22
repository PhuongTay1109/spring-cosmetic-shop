package com.cosmetics.myshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.cosmetics.myshop.model.Category;
import com.cosmetics.myshop.model.ShoppingSession;
import com.cosmetics.myshop.model.User;
import com.cosmetics.myshop.service.CartItemService;
import com.cosmetics.myshop.service.CategoryService;
import com.cosmetics.myshop.service.ShoppingSessionService;
import com.cosmetics.myshop.utils.StringUtils;

@ControllerAdvice(annotations = Controller.class)
public class GlobalControllerAdvice {
	@Autowired
	CategoryService categoryService;

	@ModelAttribute("categoryList")
	public List<Category> getCategoryList() {
		return categoryService.findAllCategories();
	}

	@ModelAttribute("stringUtils")
	public StringUtils getStringUtils() {

		StringUtils stringUtils = new StringUtils();
		return stringUtils;
	}
	
	@Autowired
	CartItemService cartItemService;
	@Autowired ShoppingSessionService shoppingSessionService;
	
	@ModelAttribute("totalCartItems")
	public int getTotalCartItems(Authentication authentication) {
		// If user hasn't login, the total is 0
		if(authentication == null)
			return 0;
		
		User user = (User) authentication.getPrincipal();		
		int userId = user.getUserId();
		ShoppingSession shoppingSession = shoppingSessionService.findShoppingSessionByUserId(userId);
		
		
		// If user hasn't added to cart any items, the total is 0
		if(shoppingSession == null)
			return 0;
		
		return cartItemService.countTotalQuantitByShoppingSession(shoppingSession.getId());
	}
}
