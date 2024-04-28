package com.cosmetics.myshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cosmetics.myshop.model.ShoppingSession;

import jakarta.transaction.Transactional;

public interface ShoppingSessionRepository extends JpaRepository<ShoppingSession, Integer> {

	@Query("SELECT s FROM ShoppingSession s WHERE s.user.userId = :userId")
    Optional<ShoppingSession> findByUserId(@Param("userId") int userId);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM ShoppingSession s WHERE s.id =:shoppingSessionId")
	void deleteShoppingSession(int shoppingSessionId);
}
