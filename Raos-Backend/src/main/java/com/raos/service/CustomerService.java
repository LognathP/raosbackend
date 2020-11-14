package com.raos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.raos.model.CustomerProfile;
import com.raos.model.Customer;
import com.raos.model.CustomerAddress;
import com.raos.request.AddAddressRequest;

@Service
public interface CustomerService {
	
	public Customer checkCustomer(Customer customer);
	
	public Customer addCustomer(Customer customer);
	
	public int addCustomerAddress(AddAddressRequest req);

	public boolean updateCustomerAddress(AddAddressRequest addAddrReq, int address_id);

	public boolean deleteCustomerAddress(int address_id);

	public List<CustomerAddress> getCustomerAddress(int customer_id);
	
	boolean updateCustomerProfile(CustomerProfile cpf);
	
	public CustomerProfile getCustomerProfile(int customer_id);

}
