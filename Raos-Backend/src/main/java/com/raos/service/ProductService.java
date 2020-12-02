package com.raos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.raos.model.CustomerProfile;
import com.raos.model.Product;
import com.raos.model.Customer;
import com.raos.model.CustomerAddress;
import com.raos.request.AddAddressRequest;

@Service
public interface ProductService {
	
	public List<Object> getCategory();

	//public List<Object> getSubCategory(int categoryId);

	public List<Product> getProducts(int categoryId);

	

}
