package com.cosmetics.myshop.service;

import java.util.List;

import com.cosmetics.myshop.dto.OrderItemsDTO;
import com.cosmetics.myshop.model.OrderItems;

public interface OrderItemsService {

	public void save (OrderItems orderItem);
	
	public List<OrderItemsDTO> findByOrderId(Integer orderId);
}
