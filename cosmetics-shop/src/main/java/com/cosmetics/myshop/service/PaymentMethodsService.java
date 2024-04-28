package com.cosmetics.myshop.service;

import java.util.Optional;

import com.cosmetics.myshop.model.PaymentMethods;

public interface PaymentMethodsService {
	public PaymentMethods findById(int id);

}
