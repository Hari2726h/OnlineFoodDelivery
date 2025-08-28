package com.foodexpress.service;

import java.util.List;

import com.foodexpress.exception.CustomerNotFound;
import com.foodexpress.model.Customer;

public interface CustomerService {
	
    public Customer addCustomer(Customer customer)throws CustomerNotFound;
	
	public Customer updateCustomer(Customer customer)throws CustomerNotFound;
	
	public Customer removeCustomer(Integer customerId)throws CustomerNotFound;
	
	public Customer viewCustomer(Integer customerId)throws CustomerNotFound;

    public List<Customer>  viewAllCustomer()throws CustomerNotFound;
    
}
 