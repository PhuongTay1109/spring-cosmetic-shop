package com.cosmetics.myshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cosmetics.myshop.model.PaymentMethods;


@Repository
public interface PaymentMethodsRepository extends JpaRepository<PaymentMethods, Integer> {
	
	Optional<PaymentMethods> findById(int id);

}
