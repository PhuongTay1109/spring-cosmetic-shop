package com.cosmetics.myshop.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosmetics.myshop.dto.OrderDetailsDTO;
import com.cosmetics.myshop.dto.OrderItemsDTO;
import com.cosmetics.myshop.model.OrderDetails;
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

	@Override
	public List<OrderItemsDTO> findByOrderId(Integer orderId) {
		List<OrderItems> orderItemsList = orderItemsRepository.findByOrderId(orderId);
		return orderItemsList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
	}

	private OrderItemsDTO convertToDTO(OrderItems orderItems) {
        OrderItemsDTO dto = new OrderItemsDTO();
        dto.setTotal(orderItems.getTotal());
        dto.setCreatedAt(orderItems.getCreatedAt());
        dto.setModifiedAt(orderItems.getModifiedAt());
        dto.setQuantity(orderItems.getQuantity());
        dto.setProductId(orderItems.getProduct().getId());
        dto.setName(orderItems.getProduct().getName());
        dto.setPrice(orderItems.getProduct().getPrice());
        dto.setImageLink(orderItems.getProduct().getImageLink());
        dto.setDescription(orderItems.getProduct().getDescription());
        dto.setTagList(orderItems.getProduct().getTagList());
        dto.setCategoryName(orderItems.getProduct().getCategoryName());
        return dto;
    }
}
