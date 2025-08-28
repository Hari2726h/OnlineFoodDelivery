package com.foodexpress.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodexpress.model.Address;
import com.foodexpress.model.Restaurant;

@Repository
public interface RestaurantDao extends JpaRepository<Restaurant, Integer>{

	public List<Restaurant> findByAddress(Address address);

	public Restaurant findByMobileNumber(String mobileNo);
	
	
}
