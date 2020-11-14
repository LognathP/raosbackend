package com.raos.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raos.business.CustomerBusiness;
import com.raos.logger.CommonLogger;
import com.raos.model.CustomerProfile;
import com.raos.request.AddAddressRequest;

@RestController
public class CustomerController {
	
private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	

	@Autowired
	CommonLogger Logger;
	
	@Autowired
	CustomerBusiness customerBusiness;

	@PostMapping("/api/customer/addAddress")
	public Object addAddress(@RequestBody AddAddressRequest addAddrReq) {
		Logger.info(this.getClass(),"ADD ADDRESS CUSTOMER API CALL STARTED AT "+dateFormat.format(new Date()));
		return customerBusiness.addAddress(addAddrReq);
	}
	@PostMapping("/api/customer/updateAddress")
	public Object updateAddress(@RequestBody AddAddressRequest addAddrReq,@RequestParam int address_id) {
		Logger.info(this.getClass(),"UPDATE ADDRESS CUSTOMER API CALL STARTED AT "+dateFormat.format(new Date()));
		return customerBusiness.updateAddress(addAddrReq,address_id);
	}
	@PostMapping("/api/customer/deleteAddress")
	public Object deleteAddress(@RequestParam int address_id) {
		Logger.info(this.getClass(),"DELETE ADDRESS CUSTOMER API CALL STARTED AT "+dateFormat.format(new Date()));
		return customerBusiness.deleteAddress(address_id);
	}
	@PostMapping("/api/customer/getAddress")
	public Object getAddress(@RequestParam int customer_id) {
		Logger.info(this.getClass(),"GET ADDRESS CUSTOMER API CALL STARTED AT "+dateFormat.format(new Date()));
		return customerBusiness.getAddress(customer_id);
	}
	@PostMapping("/api/customer/getprofile")
	public Object getCustomerProfile(@RequestParam int customer_id) {
		Logger.info(this.getClass(),"GET CUSTOMER PROFILE API CALL STARTED AT "+dateFormat.format(new Date()));
		return customerBusiness.getCustomerProfile(customer_id);
	}
	@PostMapping("/api/customer/updateprofile")
	public Object updateCustomerProfile(@RequestBody CustomerProfile cpf) {
		Logger.info(this.getClass(),"UPDATE CUSTOMER PROFILE API CALL STARTED AT "+dateFormat.format(new Date()));
		return customerBusiness.updateCustomerProfile(cpf);
	}


	
}
