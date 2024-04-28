package com.cosmetics.myshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosmetics.myshop.model.CartItem;
import com.cosmetics.myshop.model.ShoppingSession;
import com.cosmetics.myshop.repository.ShoppingSessionRepository;
import com.cosmetics.myshop.service.ShoppingSessionService;

@Service
public class ShoppingSessionServiceImpl implements ShoppingSessionService {
	
	@Autowired
	ShoppingSessionRepository shoppingSessionRepository;
	
	@Override
	public ShoppingSession findShoppingSessionByUserId(int userId) {
		Optional<ShoppingSession> shoppingSession = shoppingSessionRepository.findByUserId(userId);
		if (shoppingSession.isEmpty()) {
			return null;
		}
		return shoppingSession.get();
	}
	
	@Override
	public void saveShoppingSession(ShoppingSession shoppingSession) {
		shoppingSessionRepository.save(shoppingSession);
	}

	@Override
	public void deleteShoppingSession(int shoppingSessionId) {
		shoppingSessionRepository.deleteShoppingSession(shoppingSessionId);
	}
}
