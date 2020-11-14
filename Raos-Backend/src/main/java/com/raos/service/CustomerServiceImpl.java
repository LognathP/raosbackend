package com.raos.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.raos.dao.CustomerDao;
import com.raos.logger.CommonLogger;
import com.raos.model.Customer;
import com.raos.model.CustomerAddress;
import com.raos.model.CustomerProfile;
import com.raos.repository.CustomerRepository;
import com.raos.request.AddAddressRequest;

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


	@Override
	public int addCustomerAddress(AddAddressRequest req) {
		int i = 0;
		try {
			i = customerDao.addCustomerAddress(req);
		} catch (Exception e) {
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE addCustomerAddress "+e.getMessage().toString());
			e.printStackTrace();
		}
		return i;
	}


	@Override
	public boolean updateCustomerAddress(AddAddressRequest addAddrReq, int address_id) {
		boolean status = false;
		try {
			status = customerDao.updateCustomerAddress(addAddrReq,address_id);
		} catch (Exception e) {
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE updateCustomerAddress "+e.getMessage().toString());
			e.printStackTrace();
		}
		return status;
	}
	
	@Override
	public boolean deleteCustomerAddress(int address_id) {
		boolean status = false;
		try {
			status = customerDao.deleteCustomerAddress(address_id);
		} catch (Exception e) {
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE deleteCustomerAddress "+e.getMessage().toString());
			e.printStackTrace();
		}
		return status;
	}
	
	@Override
	public List<CustomerAddress> getCustomerAddress(int customer_id) {
		return customerDao.getCustomerAddress(customer_id);
	}
	@Override
	public CustomerProfile getCustomerProfile(int customer_id) {
		return customerDao.getCustomerProfile(customer_id);
	}
	
	@Override
	public boolean updateCustomerProfile(CustomerProfile cpf) {
			return customerDao.updateCustomerProfile(cpf);
	}
	


	

}
