package com.raos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.raos.dao.CustomerDao;
import com.raos.logger.CommonLogger;
import com.raos.model.Customer;
import com.raos.repository.CustomerRepository;

@Component
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CommonLogger LOGGER;
	
	@Autowired
	CustomerDao customerDao;
	
	@Autowired
	CustomerRepository customerRepository;
	
	
	@Override
	public Customer checkCustomer(Customer customer) {
		try {
			customer = customerDao.checkCustomer(customer);
		} catch (Exception e) {
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE checkUser "+e.getMessage().toString());
			e.printStackTrace();
		}
		return customer;
	}


	@Override
	public Customer addCustomer(Customer customer) {
		try {
			customer = customerRepository.save(customer);
		} catch (Exception e) {
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE ADDING/UPDATING CUSTOMER "+e.getMessage().toString());
			e.printStackTrace();
		}
		return customer;
	}


	
	


	

}
