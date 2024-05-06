package com.cosmetics.myshop.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosmetics.myshop.dto.OrderDetailsDTO;
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

	@Override
	public List<OrderDetailsDTO> findByUserId(Integer userId) {
		List<OrderDetails> orderDetailsList = orderDetailsRepository.findByUserId(userId);
        return orderDetailsList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
	}
	
	private OrderDetailsDTO convertToDTO(OrderDetails orderDetails) {
        OrderDetailsDTO dto = new OrderDetailsDTO();
        dto.setId(orderDetails.getId());
        dto.setTotal(orderDetails.getTotal());
        dto.setCreatedAt(orderDetails.getCreatedAt());
        if(orderDetails.getPaymentMethod().getId() == 1) {dto.setPaymentMethod("VNPay");}	
        else { dto.setPaymentMethod("Cash");}
        dto.setName(orderDetails.getName());
        dto.setAddress(orderDetails.getAddress());
        dto.setPhone(orderDetails.getPhone());
        return dto;
    }

}
