package com.cosmetics.myshop.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cosmetics.myshop.dto.CartItemDTO;
import com.cosmetics.myshop.model.CartItem;
import com.cosmetics.myshop.model.Product;
import com.cosmetics.myshop.model.ShoppingSession;

import jakarta.transaction.Transactional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
	@Modifying
	@Transactional
	@Query("INSERT INTO CartItem (shoppingSession, product, quantity, createdAt) " +
	       "SELECT ss, p, :quantity, :createdAt " +
	       "FROM ShoppingSession ss, Product p " +
	       "WHERE ss.id = :shoppingSessionId AND p.id = :productId")
	void addToCart(Integer shoppingSessionId, Integer productId, Integer quantity, Date createdAt);

	@Query("SELECT ci.product, ci.quantity FROM CartItem ci WHERE ci.shoppingSession.id = :shoppingSessionId")
    List<Object[]> findProductsAndQuantitiesByShoppingSessionId(Integer shoppingSessionId);

	@Query("SELECT ci "
			+ "FROM CartItem ci WHERE ci.shoppingSession.id = :shoppingSessionId "
			+ "and ci.product.id = :productId")
	CartItem findCartItem(Integer shoppingSessionId, Integer productId);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM CartItem ci WHERE ci.product.id = :productId "
			+ "and ci.shoppingSession.id = :shoppingSessionId")
	void deleteCartItem(Integer shoppingSessionId, Integer productId);

	@Modifying
    @Transactional
    @Query("UPDATE CartItem ci SET ci.quantity = :quantity "
    		+ "WHERE ci.shoppingSession.id = :shoppingSessionId "
    		+ "AND ci.product.id = :productId")
    void updateCartItemQuantity(Integer shoppingSessionId, Integer productId, Integer quantity);
	
	@Query("select sum(ci.quantity) as totalQuantity from CartItem ci where ci.shoppingSession.id = :shoppingSessionId")
	int countTotalQuantitByShoppingSession(Integer shoppingSessionId);
}
