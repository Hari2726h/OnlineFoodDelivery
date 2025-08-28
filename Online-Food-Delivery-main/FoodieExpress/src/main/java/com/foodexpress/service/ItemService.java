package com.foodexpress.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.foodexpress.exception.ItemNotFoundException;
import com.foodexpress.exception.RestaurantException;
import com.foodexpress.model.Category;
import com.foodexpress.model.Item;
import com.foodexpress.model.Restaurant;

public interface ItemService {

	public Item addItem(Item item) throws ItemNotFoundException;
	
	public Item updateItem(Item item) throws ItemNotFoundException;
	
	public Item viewItem (Integer itemId) throws ItemNotFoundException;
	
	public Item removeItem(Integer itemId) throws ItemNotFoundException;
	
	public List<Item> viewAllItemsByCategory(Category category) throws ItemNotFoundException;
	
	public List<Item> viewAllItemsByRetaurant(Restaurant restaurant) throws ItemNotFoundException,RestaurantException;
	
	public List<Item> viewAllItemsByName(String name) throws ItemNotFoundException;
 	
}
 