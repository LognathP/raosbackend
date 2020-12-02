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
import com.raos.model.Product;
import com.raos.request.AddAddressRequest;
import com.raos.service.CustomerService;
import com.raos.service.ProductService;

import io.swagger.v3.core.util.Json;

@Component
public class ProductBusiness {

	@Autowired
	CommonLogger LOGGER;

	@Autowired
	ConfigProperties configProp;

	@Autowired
	CommonResponse commonResponse;

	@Autowired
	ProductService productService;

	@Autowired
	CommonDataResponse commonDataResponse;

	@Autowired
	CommonSingleResponse commonSingleResponse;

	
	public Object getCategory() {
		List<Object> clist = productService.getCategory();
		if (clist.size() > 0) {
			LOGGER.info(this.getClass(), "CATEGORIES LISTED SUCCESSFULLY");
			commonSingleResponse.setStatus(HttpStatus.OK.toString());
			commonSingleResponse.setMessage("Categories Listed Successfully");
			commonSingleResponse.setData(clist);
			return new ResponseEntity<CommonSingleResponse>(commonSingleResponse, HttpStatus.OK);
		} else {
			LOGGER.error(this.getClass(), "NO CATEGORIES NOT FOUND");
			commonResponse.setStatus(HttpStatus.NOT_FOUND.toString());
			commonResponse.setMessage("No Categories found");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		}
	}

//	public Object getSubCategory(int categoryId) {
//		List<Object> sclist  = productService.getSubCategory(categoryId);
//		if (sclist.size() > 0) {
//			LOGGER.info(this.getClass(), "SUB CATEGORIES LISTED SUCCESSFULLY");
//			commonSingleResponse.setStatus(HttpStatus.OK.toString());
//			commonSingleResponse.setMessage("Sub Categories Listed Successfully");
//			commonSingleResponse.setData(sclist);
//			return new ResponseEntity<CommonSingleResponse>(commonSingleResponse, HttpStatus.OK);
//		} else {
//			LOGGER.error(this.getClass(), "NO SUB CATEGORIES NOT FOUND");
//			commonResponse.setStatus(HttpStatus.NOT_FOUND.toString());
//			commonResponse.setMessage("No Sub Categories found");
//			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
//		}
//	}

	public Object getProducts(int categoryId) {
		List<Product> plist  = productService.getProducts(categoryId);
		if (plist.size() > 0) {
			LOGGER.info(this.getClass(), "PRODUCTS LISTED SUCCESSFULLY");
			commonSingleResponse.setStatus(HttpStatus.OK.toString());
			commonSingleResponse.setMessage("Products Listed Successfully");
			commonSingleResponse.setData(plist);
			return new ResponseEntity<CommonSingleResponse>(commonSingleResponse, HttpStatus.OK);
		} else {
			LOGGER.error(this.getClass(), "NO PRODUCTS NOT FOUND");
			commonResponse.setStatus(HttpStatus.NOT_FOUND.toString());
			commonResponse.setMessage("No Products found");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		}
	}


	
}
