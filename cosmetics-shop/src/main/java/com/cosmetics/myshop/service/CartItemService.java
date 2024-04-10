package com.cosmetics.myshop.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosmetics.myshop.dto.CartItemDTO;
import com.cosmetics.myshop.model.CartItem;
import com.cosmetics.myshop.model.Product;
import com.cosmetics.myshop.repository.CartItemRepository;
import com.cosmetics.myshop.repository.ProductRepository;

@Service
public class CartItemService {
	@Autowired
	CartItemRepository cartItemRepository;
	
	public void addToCart(int shoppingSessionId, int productId, int quantity, Date createdAt) {
		cartItemRepository.addToCart(shoppingSessionId, productId, quantity, createdAt);
	}
	
	public List<CartItemDTO> findAllCartItems(int shoppingSessionId) {
		return cartItemRepository.findAllCartItems(shoppingSessionId);
	}
	
	public CartItem findExistingCartItem(Integer shoppingSessionId, Integer productId) {
		return cartItemRepository.findExistingCartItem(shoppingSessionId, productId);
	}
	
	public void deleteCartItem(Integer shoppingSessionId, Integer productId) {
		cartItemRepository.deleteCartItem(shoppingSessionId, productId);
	}
	
	public void updateCartItemQuantity(Integer shoppingSessionId, Integer productId, Integer quantity) {
		cartItemRepository.updateCartItemQuantity(shoppingSessionId, productId, quantity);
	}
	

}
