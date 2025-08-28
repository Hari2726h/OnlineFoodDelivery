package com.foodexpress.service;

import com.foodexpress.exception.LoginException;
import com.foodexpress.model.LoginDto;

public interface LoginService {
	
	public String loginAccount(LoginDto dto) throws LoginException;
	
	public String LogOutFromAccount(String key) throws LoginException;

}
 