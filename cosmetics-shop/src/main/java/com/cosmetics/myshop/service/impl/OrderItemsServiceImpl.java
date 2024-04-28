package com.cosmetics.myshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosmetics.myshop.model.OrderItems;
import com.cosmetics.myshop.repository.OrderItemsRepository;
import com.cosmetics.myshop.service.OrderItemsService;

@Service
public class OrderItemsServiceImpl implements OrderItemsService {
	
	@Autowired
	OrderItemsRepository orderItemsRepository;

	@Override
	public void save(OrderItems orderItem) {
		orderItemsRepository.save(orderItem);
		
	}

}
