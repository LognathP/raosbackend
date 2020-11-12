package com.raos.dao;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.raos.model.Customer;

@Component
public interface CustomerDao {
	
	public Customer checkCustomer(Customer customer);

	}
