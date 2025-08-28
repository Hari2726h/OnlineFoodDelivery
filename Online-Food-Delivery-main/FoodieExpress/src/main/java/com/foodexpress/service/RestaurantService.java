package com.foodexpress.service;

import java.util.List;

import com.foodexpress.exception.RestaurantException;
import com.foodexpress.model.Address;
import com.foodexpress.model.Restaurant;

public interface RestaurantService {
	
	public Restaurant addRestaurant(Restaurant res) throws RestaurantException;
	
	public Restaurant updateRestaurant(Restaurant res) throws RestaurantException;
	
	public Restaurant viewRestaurant(Integer resId) throws RestaurantException;
	
	public List<Restaurant> viewNearByRestaurant(Address address) throws RestaurantException;
	
	public List<Restaurant> viweRestaurantByItemName(String itemName) throws RestaurantException;
	
	

}
 