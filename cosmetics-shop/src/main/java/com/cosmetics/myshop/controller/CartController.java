package com.cosmetics.myshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {
	@GetMapping("/cart")
	public String getCart() {
		return "user/cart";
	}
}
