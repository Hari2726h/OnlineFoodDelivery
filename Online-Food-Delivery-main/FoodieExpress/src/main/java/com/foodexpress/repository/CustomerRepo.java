package com.foodexpress.repository;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import com.foodexpress.model.Customer;

@Repository
public interface CustomerRepo extends JpaRepositoryImplementation<Customer, Integer>{

	public Customer findByEmail(String email);

	public Customer findByMobileNumber(String mobileNo);
	
}
