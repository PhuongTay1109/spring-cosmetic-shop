package com.cosmetics.myshop.dto;

import com.cosmetics.myshop.model.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
	private Product product;
	private int productId;
	private int quantity;
}
