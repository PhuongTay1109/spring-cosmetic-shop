package com.cosmetics.myshop.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDataDTO {
	List<CartItemDTO> orderList;
	int paymentMethodId;
	String name;
	String phone;
	String address;

}
