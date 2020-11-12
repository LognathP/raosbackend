package com.raos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.raos.model.UserDeviceToken;

@Service
public interface UserDeviceTokenService {
	
	public boolean addUserToken(UserDeviceToken userToken);
	
	

}
