package com.raos.business;


import java.util.List;
import java.util.Optional;

import org.checkerframework.checker.units.qual.m;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.raos.constants.CommonConstants;
import com.raos.logger.ConfigProperties;
import com.raos.logger.CommonLogger;
import com.raos.model.CommonDataResponse;
import com.raos.model.CommonResponse;
import com.raos.model.CommonSingleResponse;
import com.raos.model.Customer;
import com.raos.model.UserDeviceToken;
import com.raos.service.CustomerService;
import com.raos.service.OtpService;
import com.raos.service.UserDeviceTokenService;
import com.raos.util.DESEncryptor;

import io.swagger.v3.core.util.Json;

@Component
public class LoginBusiness {

	@Autowired
	CommonLogger LOGGER;
	
	@Autowired
	ConfigProperties configProp;
	
	@Autowired
	CommonResponse commonResponse;
	
	@Autowired
	OtpService otpService;
	
	
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	CommonDataResponse commonDataResponse;
	
	@Autowired
	CommonSingleResponse commonSingleResponse;
	
	@Autowired
	UserDeviceTokenService userDeviceTokenService;
	
	
	@SuppressWarnings("unchecked")
	public ResponseEntity loginCustomer(String mobileNum)
	{
		LOGGER.info(this.getClass(),"LOGIN CUSTOMER BUSINESS LAYER");
		Customer customer = new Customer();
		customer.setMobileno(mobileNum);
		customer = customerService.checkCustomer(customer);
		if(customer.getId() == null)
		{
			LOGGER.info(this.getClass(),"ADDING NEW CUSTOMER");
			customer = customerService.addCustomer(customer);
		}
			LOGGER.info(this.getClass(),"GENERATE OTP BUSINESS LAYER");
			if(otpService.sendOtp(mobileNum, otpService.generateOTP(String.valueOf(customer.getId()))).startsWith("S"))
			{
				LOGGER.info(this.getClass(),"OTP GENERATED SUCCESSFULLY");
				commonSingleResponse.setStatus(HttpStatus.OK.toString());
				commonSingleResponse.setMessage("OTP Sent Successfully");
				JSONObject js = new JSONObject();
				js.put("customer_id", customer.getId());
				js.put("membership_flag", customer.getMembership_flag());
				commonSingleResponse.setData(js);
				return new ResponseEntity<CommonSingleResponse>(commonSingleResponse,HttpStatus.OK);
			}
			else
			{
				LOGGER.error(this.getClass(),"OTP GENERATION FAILED");
				commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				commonResponse.setMessage("Error occured in OTP Send, Please try Again");
				return new ResponseEntity<CommonResponse>(commonResponse,HttpStatus.OK);	
			}	

	}

	public ResponseEntity verifyCustomerLogin(int customer_id, String otp, String device_token, int device_type) {
		LOGGER.info(this.getClass(),"OTP VERIFICATION BUSINESS LAYER");
		if(otpService.getOtp(String.valueOf(customer_id)) == Integer.valueOf(otp))
		{
			UserDeviceToken ut = new UserDeviceToken();
			ut.setCustomer_id(customer_id);
			ut.setDevice_token(device_token);
			ut.setDevice_type(device_type);
			userDeviceTokenService.addUserToken(ut);
			LOGGER.info(this.getClass(),"LOGIN CHECK SUCCESS");
			commonSingleResponse.setStatus(HttpStatus.OK.toString());
			commonSingleResponse.setMessage("login_success");
			JSONObject js = new JSONObject();
			js.put("token", device_token);
			commonSingleResponse.setData(js);
			return new ResponseEntity<CommonSingleResponse>(commonSingleResponse,HttpStatus.OK);
		}
		else
		{
			LOGGER.info(this.getClass(),"OTP VERIFICATION FAILED");
			commonResponse.setStatus(HttpStatus.UNAUTHORIZED.toString());
			commonResponse.setMessage("invalid_credentials");
			return new ResponseEntity<CommonResponse>(commonResponse,HttpStatus.OK);
		}
	}
	
}
