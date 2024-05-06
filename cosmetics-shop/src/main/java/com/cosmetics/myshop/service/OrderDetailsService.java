package com.cosmetics.myshop.service;

import java.util.List;

import com.cosmetics.myshop.dto.OrderDetailsDTO;
import com.cosmetics.myshop.model.OrderDetails;

public interface OrderDetailsService {
	public void save(OrderDetails orderDetails);
	public List<OrderDetailsDTO> findByUserId(Integer userId);
}
