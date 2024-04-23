package com.cosmetics.myshop.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosmetics.myshop.dto.CartItemDTO;
import com.cosmetics.myshop.model.CartItem;
import com.cosmetics.myshop.model.Product;
import com.cosmetics.myshop.repository.CartItemRepository;
import com.cosmetics.myshop.repository.ProductRepository;
import com.cosmetics.myshop.service.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService {
	@Autowired
	CartItemRepository cartItemRepository;
	
	public void addToCart(int shoppingSessionId, int productId, int quantity, Date createdAt) {
		cartItemRepository.addToCart(shoppingSessionId, productId, quantity, createdAt);
	}
	
	public List<Object[]> findProductsAndQuantitiesByShoppingSessionId(Integer shoppingSessionId) {
		return cartItemRepository.findProductsAndQuantitiesByShoppingSessionId(shoppingSessionId);
	}
	
	public CartItem findCartItem(Integer shoppingSessionId, Integer productId) {
		return cartItemRepository.findCartItem(shoppingSessionId, productId);
	}
	
	public void deleteCartItem(Integer shoppingSessionId, Integer productId) {
		cartItemRepository.deleteCartItem(shoppingSessionId, productId);
	}
	
	public void updateCartItemQuantity(Integer shoppingSessionId, Integer productId, Integer quantity) {
		cartItemRepository.updateCartItemQuantity(shoppingSessionId, productId, quantity);
	}

	@Override
	public int countTotalQuantityByShoppingSession(Integer shoppingSessionId) {
		return cartItemRepository.countTotalQuantityByShoppingSession(shoppingSessionId);
	}
}
