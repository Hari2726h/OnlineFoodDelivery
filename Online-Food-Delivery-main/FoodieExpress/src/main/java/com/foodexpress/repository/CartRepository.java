package com.foodexpress.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodexpress.model.Customer;
import com.foodexpress.model.FoodCart;

@Repository
public interface CartRepository extends JpaRepository<FoodCart, String> {

	public FoodCart findByCustomer(Customer customer);
	
}
