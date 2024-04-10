package com.cosmetics.myshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosmetics.myshop.model.CartItem;
import com.cosmetics.myshop.model.ShoppingSession;
import com.cosmetics.myshop.repository.ShoppingSessionRepository;

@Service
public class ShoppingSessionService {
	
	@Autowired
	ShoppingSessionRepository shoppingSessionRepository;
	
	public ShoppingSession findShoppingSessionByUserId(int userId) {
		Optional<ShoppingSession> shoppingSession = shoppingSessionRepository.findByUserId(userId);
		if (shoppingSession.isEmpty()) {
			return null;
		}
		return shoppingSession.get();
	}
	
	public void saveShoppingSession(ShoppingSession shoppingSession) {
		shoppingSessionRepository.save(shoppingSession);
	}
}
