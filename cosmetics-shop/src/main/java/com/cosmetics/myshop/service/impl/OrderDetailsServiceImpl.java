package com.cosmetics.myshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosmetics.myshop.model.OrderDetails;
import com.cosmetics.myshop.repository.OrderDetailsRepository;
import com.cosmetics.myshop.service.OrderDetailsService;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {
	@Autowired 
	OrderDetailsRepository orderDetailsRepository;

	@Override
	public void save(OrderDetails orderDetails) {
		orderDetailsRepository.save(orderDetails);
	}

}
