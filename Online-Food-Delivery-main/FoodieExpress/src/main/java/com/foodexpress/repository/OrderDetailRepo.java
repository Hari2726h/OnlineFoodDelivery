package com.foodexpress.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodexpress.model.FoodCart;
import com.foodexpress.model.OrderDetails;


@Repository
public interface OrderDetailRepo extends JpaRepository<OrderDetails, Integer>{
	
	public List<OrderDetails >findByCart(FoodCart cart);

}
