package com.raos.service;

import org.springframework.stereotype.Service;

import com.raos.model.UserDeviceToken;

@Service
public interface UserDeviceTokenService {
	
	public boolean addUserToken(UserDeviceToken userToken);
	
	

}
