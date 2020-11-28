package com.raos.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.raos.business.StoreBusiness;
import com.raos.logger.CommonLogger;
import com.raos.request.DeliverySlotsRequest;

@RestController
public class StoreController {
	
private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	

	@Autowired
	CommonLogger Logger;
	
	@Autowired
	StoreBusiness storeBusiness;

	@PostMapping("/api/store/getDelivery")
	public Object getDelivery() {
		Logger.info(this.getClass(),"GET DELIVERY DETAILS API CALL STARTED AT "+dateFormat.format(new Date()));
		return storeBusiness.getDelivery();
	}
	
	@PostMapping("/api/store/updateDeliverySlots")
	public Object updateDeliveryDetails(@RequestBody DeliverySlotsRequest slotsRequests) {
		Logger.info(this.getClass(),"UPDATE DELIVERY DETAILS API CALL STARTED AT "+dateFormat.format(new Date()));
		return storeBusiness.updateDeliveryDetails(slotsRequests);
	}
	
	@PostMapping("/api/store/listproducts")
	public Object listProducts() {
		Logger.info(this.getClass(),"LIST PRODUCTS DETAILS API CALL STARTED AT "+dateFormat.format(new Date()));
		return storeBusiness.listProducts();
	}
	@PostMapping(value="/api/store/uploadproducts",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public Object uploadProducts(@RequestParam("file") MultipartFile file) {
		Logger.info(this.getClass(),"UPLOAD PRODUCTS DETAILS API CALL STARTED AT "+dateFormat.format(new Date()));
		return storeBusiness.uploadProducts(file);
	}

	@PostMapping("/api/store/editproduct")
	public Object editProduct(@RequestParam int product_id,@RequestParam String product_name,
			@RequestParam String img_url,@RequestParam int discount_flag,@RequestParam int status) {
		Logger.info(this.getClass(),"EDIT PRODUCT API CALL STARTED AT "+dateFormat.format(new Date()));
		return storeBusiness.editProducts(product_id,product_name,img_url,discount_flag,status);
	}
	@PostMapping("/api/store/deleteproduct")
	public Object deleteProduct(@RequestParam int product_id) {
		Logger.info(this.getClass(),"DELETE PRODUCT API CALL STARTED AT "+dateFormat.format(new Date()));
		return storeBusiness.deleteProduct(product_id);
	}
	@PostMapping("/api/store/listoffers")
	public Object listOffers() {
		Logger.info(this.getClass(),"LIST OFFERS API CALL STARTED AT "+dateFormat.format(new Date()));
		return storeBusiness.listOffers();
	}
	@PostMapping("/api/store/addoffer")
	public Object addOffer(@RequestParam String offer_name,@RequestParam String offer_code,
			@RequestParam java.sql.Date offer_expiry) {
		Logger.info(this.getClass(),"ADD OFFER API CALL STARTED AT "+dateFormat.format(new Date()));
		return storeBusiness.addOffer(offer_name,offer_code,offer_expiry);
	}
	@PostMapping("/api/store/editoffer")
	public Object editOffer(@RequestParam int offer_id,@RequestParam String offer_name,@RequestParam String offer_code,
			@RequestParam java.sql.Date offer_expiry,@RequestParam int offer_status) {
		Logger.info(this.getClass(),"EDIT OFFER API CALL STARTED AT "+dateFormat.format(new Date()));
		return storeBusiness.editOffer(offer_id,offer_name,offer_code,offer_expiry,offer_status);
	}
	@PostMapping("/api/store/deleteoffer")
	public Object deleteOffer(@RequestParam int offer_id) {
		Logger.info(this.getClass(),"DELETE OFFER API CALL STARTED AT "+dateFormat.format(new Date()));
		return storeBusiness.deleteOffer(offer_id);
	}

	
}
