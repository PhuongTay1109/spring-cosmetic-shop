package com.cosmetics.myshop.service;

import com.cosmetics.myshop.model.ShoppingSession;

public interface ShoppingSessionService {

	ShoppingSession findShoppingSessionByUserId(int userId);

	void saveShoppingSession(ShoppingSession shoppingSession);
	
	void deleteShoppingSession(int shoppingSessionId);

}