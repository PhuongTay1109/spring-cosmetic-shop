package com.cosmetics.myshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cosmetics.myshop.model.OrderDetails;
import com.cosmetics.myshop.model.User;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {
	
	@Query("SELECT o FROM OrderDetails o WHERE o.user.userId = :userId")
	List<OrderDetails> findByUserId(Integer userId);
	
}
