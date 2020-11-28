package com.raos.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.raos.dao.CustomerDao;
import com.raos.dao.ProductDao;
import com.raos.logger.CommonLogger;
import com.raos.model.Customer;
import com.raos.model.CustomerAddress;
import com.raos.model.CustomerProfile;
import com.raos.model.Product;
import com.raos.model.ProductUpload;
import com.raos.repository.CustomerRepository;
import com.raos.repository.ProductsRepository;
import com.raos.request.AddAddressRequest;
import com.raos.util.CSVHelper;

@Component
public class ProductServiceImpl implements ProductService {

	@Autowired
	CommonLogger LOGGER;
	
	@Autowired
	ProductDao productDao;
	
	@Autowired
	ProductsRepository productRepo;

	@Override
	public List<Object> getCategory() {
		return productDao.getCategory();
	}
	@Override
	public List<Object> getSubCategory(int categoryId) {
		return productDao.getSubCategory(categoryId);
	}
	@Override
	public List<Product> getProducts(int subCategoryId) {
		return productDao.getProducts(subCategoryId);
	}
	
	@Override
    public void save(MultipartFile file) {
	    try {
	      List<ProductUpload> products = CSVHelper.csvToProducts(file.getInputStream());
	      productRepo.saveAll(products);
	    } catch (Exception e) {
	      LOGGER.info(this.getClass(),"FAILED TO STORE CSV " + e.getMessage());
	      throw new RuntimeException("FAILED TO STORE CSV " + e.getMessage());
	    
	    }
	  }
		



	

}
