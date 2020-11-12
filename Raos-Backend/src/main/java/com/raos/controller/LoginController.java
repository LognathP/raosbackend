package com.raos.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raos.business.LoginBusiness;
import com.raos.logger.CommonLogger;
import com.raos.model.CommonDataResponse;
import com.raos.model.CommonResponse;
import com.raos.model.CommonSingleResponse;


@RestController
public class LoginController {
	
private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	

	@Autowired
	CommonLogger Logger;
	
	@Autowired
	LoginBusiness loginBusiness;

	@PostMapping("/api/customer/login")
	public ResponseEntity loginCustomer(@RequestParam String mobile_no) {
		Logger.info(this.getClass(),"LOGIN CUSTOMER API CALL STARTED AT "+dateFormat.format(new Date()));
		return loginBusiness.loginCustomer(mobile_no);
	}
	
	@PostMapping("/api/customer/login_verify")
	public ResponseEntity verifyCustomerLogin(@RequestParam int customer_id,@RequestParam String otp,
			@RequestParam String device_token,@RequestParam int device_type) throws Exception {
		Logger.info(this.getClass(),"OTP VERIFICATION API CALL STARTED AT "+dateFormat.format(new Date()));
		return loginBusiness.verifyCustomerLogin(customer_id,otp,device_token,device_type);
	}

	
	
}
