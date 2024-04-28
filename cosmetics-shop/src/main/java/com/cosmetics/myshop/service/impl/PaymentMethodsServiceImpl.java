package com.cosmetics.myshop.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosmetics.myshop.model.PaymentMethods;
import com.cosmetics.myshop.repository.PaymentMethodsRepository;
import com.cosmetics.myshop.service.PaymentMethodsService;

@Service
public class PaymentMethodsServiceImpl implements PaymentMethodsService {
	@Autowired
	PaymentMethodsRepository paymentMethodsRepository;

	@Override
	public PaymentMethods findById(int id) {
		Optional<PaymentMethods> pay = paymentMethodsRepository.findById(id);
		if (pay.isEmpty()) return null;
		return pay.get();
	}
	

}
