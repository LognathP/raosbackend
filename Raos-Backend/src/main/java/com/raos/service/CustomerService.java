package com.raos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.raos.model.Customer;

@Service
public interface CustomerService {
	
	public Customer checkCustomer(Customer customer);
	
	public Customer addCustomer(Customer customer);

}
