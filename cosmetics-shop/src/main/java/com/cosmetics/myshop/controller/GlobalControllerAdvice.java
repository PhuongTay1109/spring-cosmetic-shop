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

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

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
	public int getTotalCartItems(Authentication authentication, HttpServletRequest request) {
		// unauthorized user
		if(authentication == null) {
			Cookie[] cookies = request.getCookies();
	    	if (cookies != null) {
	    		for(Cookie cookie: cookies) {
	    			if(cookie.getName().equals("shoppingSessionId")) {
	    				int shoppingSessionId = Integer.parseInt(cookie.getValue());
	    				return cartItemService.countTotalQuantityByShoppingSession(shoppingSessionId);
	    			}
	    		}
	    	}
	    	return 0;
		}
		
		// authorized user
		User user = (User) authentication.getPrincipal();		
		int userId = user.getUserId();
		ShoppingSession shoppingSession = shoppingSessionService.findShoppingSessionByUserId(userId);

		// If user hasn't added to cart any items, the total is 0
		if(shoppingSession == null)
			return 0;
		
		return cartItemService.countTotalQuantityByShoppingSession(shoppingSession.getId());
	}
}
