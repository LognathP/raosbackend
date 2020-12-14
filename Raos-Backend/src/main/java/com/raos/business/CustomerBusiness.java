package com.raos.business;

import java.util.List;
import java.util.Optional;

import org.checkerframework.checker.units.qual.m;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


import com.raos.logger.CommonLogger;
import com.raos.logger.ConfigProperties;
import com.raos.model.CommonDataResponse;
import com.raos.model.CommonResponse;
import com.raos.model.CommonSingleResponse;
import com.raos.model.CustomerAddress;
import com.raos.model.CustomerProfile;
import com.raos.request.AddAddressRequest;
import com.raos.service.CustomerService;

import io.swagger.v3.core.util.Json;

@Component
public class CustomerBusiness {

	@Autowired
	CommonLogger LOGGER;

	@Autowired
	ConfigProperties configProp;

	@Autowired
	CommonResponse commonResponse;

	@Autowired
	CustomerService customerService;

	@Autowired
	CommonDataResponse commonDataResponse;

	@Autowired
	CommonSingleResponse commonSingleResponse;

	
	@SuppressWarnings("unchecked")
	public Object addAddress(AddAddressRequest addAddrReq) {
		int addrId = 0;
		JSONObject js = new JSONObject();
		if ((addrId = customerService.addCustomerAddress(addAddrReq)) > 0) {
			LOGGER.info(this.getClass(), "ADDRESS ADDED SUCCESSFULLY");
			commonSingleResponse.setStatus(HttpStatus.OK.toString());
			commonSingleResponse.setMessage("Address Added Successfully");
			js.put("address_id", addrId);
			commonSingleResponse.setData(js);
			return new ResponseEntity<CommonSingleResponse>(commonSingleResponse, HttpStatus.OK);
		} else {
			LOGGER.error(this.getClass(), "ADDRESS ADDITION FAILED");
			commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			commonResponse.setMessage("Error occured in Adding Address, Please try Again");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK).toString();
		}
	}

	public Object updateAddress(AddAddressRequest addAddrReq, int address_id) {
	
			if (customerService.updateCustomerAddress(addAddrReq, address_id)) {
				LOGGER.info(this.getClass(), "ADDRESS UPDATED SUCCESSFULLY");
				commonResponse.setStatus(HttpStatus.OK.toString());
				commonResponse.setMessage("Address Updated Successfully");
				return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
			} else {
				LOGGER.error(this.getClass(), "ADDRESS UPDATION FAILED");
				commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				commonResponse.setMessage("Error occured in Updating Address, Please try Again");
				return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
			}
		

	}

	public Object deleteAddress(int address_id) {
		
			if (customerService.deleteCustomerAddress(address_id)) {
				LOGGER.info(this.getClass(), "ADDRESS DELETED SUCCESSFULLY");
				commonResponse.setStatus(HttpStatus.OK.toString());
				commonResponse.setMessage("Address Deleted Successfully");
				return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
			} else {
				LOGGER.error(this.getClass(), "ADDRESS DELETION FAILED");
				commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				commonResponse.setMessage("Error occured in Deleting Address, Please try Again");
				return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
			}
		
	}

	public Object getAddress(int customer_id) {
	
			List<CustomerAddress> cList = customerService.getCustomerAddress(customer_id);
			if (cList.size() > 0) {
				LOGGER.info(this.getClass(), "CUSTOMER ADDRESS LISTED SUCCESSFULLY");
				commonSingleResponse.setStatus(HttpStatus.OK.toString());
				commonSingleResponse.setMessage("Customer Address Listed Successfully");
				commonSingleResponse.setData(cList);
				return new ResponseEntity<CommonSingleResponse>(commonSingleResponse, HttpStatus.OK);
			} else {
				LOGGER.error(this.getClass(), "NO CUSTOMER ADDRESS NOT FOUND");
				commonResponse.setStatus(HttpStatus.NOT_FOUND.toString());
				commonResponse.setMessage("No Customer Address found");
				return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
			}
	
	}

	

	public Object getCustomerProfile(int customer_id) {
		ResponseEntity<?> resp = null;
		CustomerProfile cPf = customerService.getCustomerProfile(customer_id);
		if (cPf.getCustomer_id() != 0) {
			LOGGER.info(this.getClass(), "CUSTOMER PROFILE RETRIEVED SUCCESSFULLY");
			commonSingleResponse.setStatus(HttpStatus.OK.toString());
			commonSingleResponse.setMessage("Customer Profile retreived Successfully");
			commonSingleResponse.setData(cPf);
			resp = new ResponseEntity<CommonSingleResponse>(commonSingleResponse, HttpStatus.OK);
		} else {
			LOGGER.error(this.getClass(), "NO CUSTOMER DETAILS FOUND");
			commonResponse.setStatus(HttpStatus.NOT_FOUND.toString());
			commonResponse.setMessage("No Customer details found");
			resp = new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		}
		return resp;
	
	}
	
	public Object updateCustomerProfile(CustomerProfile cpf) {
		ResponseEntity<?> resp = null;
		if (customerService.updateCustomerProfile(cpf)) {
			LOGGER.info(this.getClass(), "CUSTOMER PROFILE UPDATED SUCCESSFULLY");
			commonResponse.setStatus(HttpStatus.OK.toString());
			commonResponse.setMessage("Customer Profile Updated Successfully");
			resp = new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		} else {
			LOGGER.error(this.getClass(), "UNABLE TO UPDATE CUSTOMER DETAILS");
			commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			commonResponse.setMessage("Unable to updated Customer Details");
			resp = new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		}
		return resp;
		
	}

	public Object checkDeliveryAvailability(String pinCode) {
		ResponseEntity<?> resp = null;
		if (customerService.checkDeliveryAvailability(pinCode)) {
			LOGGER.info(this.getClass(), "DELIVERY AVAILABLE");
			commonResponse.setStatus(HttpStatus.OK.toString());
			commonResponse.setMessage("Delivery Available");
			resp = new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		} else {
			LOGGER.error(this.getClass(), "DELIVERY NOT AVAILABLE");
			commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			commonResponse.setMessage("Delivery Not Available");
			resp = new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		}
		return resp;
	}


	
}
