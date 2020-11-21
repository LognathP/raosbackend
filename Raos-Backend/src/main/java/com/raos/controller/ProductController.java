package com.raos.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raos.business.ProductBusiness;
import com.raos.logger.CommonLogger;

@RestController
public class ProductController {
	
private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	

	@Autowired
	CommonLogger Logger;
	
	@Autowired
	ProductBusiness productBusiness;

	@PostMapping("/api/product/getCategory")
	public Object getCategory() {
		Logger.info(this.getClass(),"GET CATEGORY API CALL STARTED AT "+dateFormat.format(new Date()));
		return productBusiness.getCategory();
	}
	@PostMapping("/api/product/getsubcategory")
	public Object getSubCategory(@RequestParam int categoryId) {
		Logger.info(this.getClass(),"GET SUB CATEGORY API CALL STARTED AT "+dateFormat.format(new Date()));
		return productBusiness.getSubCategory(categoryId);
	}
	@PostMapping("/api/product/getproducts")
	public Object getProducts(@RequestParam int subCategoryId) {
		Logger.info(this.getClass(),"GET PRODUCTS API CALL STARTED AT "+dateFormat.format(new Date()));
		return productBusiness.getProducts(subCategoryId);
	}
	


	
}
