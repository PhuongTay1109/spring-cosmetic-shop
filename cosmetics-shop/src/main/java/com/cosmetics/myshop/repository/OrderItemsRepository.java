package com.cosmetics.myshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cosmetics.myshop.model.OrderItems;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer> {
	@Query("SELECT o FROM OrderItems o WHERE o.orderDetails.id = :orderId")
	List<OrderItems> findByOrderId(Integer orderId);
}
