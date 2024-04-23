package com.cosmetics.myshop.service;

import java.util.Date;
import java.util.List;

import com.cosmetics.myshop.model.CartItem;

public interface CartItemService {
	public void addToCart(int shoppingSessionId, int productId, int quantity, Date createdAt);
	List<Object[]> findProductsAndQuantitiesByShoppingSessionId(Integer shoppingSessionId);
	public CartItem findCartItem(Integer shoppingSessionId, Integer productId);
	public void deleteCartItem(Integer shoppingSessionId, Integer productId);
	public void updateCartItemQuantity(Integer shoppingSessionId, Integer productId, Integer quantity);
	
	public int countTotalQuantityByShoppingSession(Integer shoppingSessionId);

}
