package com.raos.dao;

import java.util.List;

import org.springframework.stereotype.Component;
import com.raos.model.Customer;
import com.raos.model.CustomerAddress;
import com.raos.model.CustomerProfile;
import com.raos.model.Product;
import com.raos.request.AddAddressRequest;

@Component
public interface ProductDao {
	
	public List<Object> getCategory();

	public List<Object> getSubCategory(int categoryId);

	public List<Product> getProducts(int subCategoryId);
	
	}
