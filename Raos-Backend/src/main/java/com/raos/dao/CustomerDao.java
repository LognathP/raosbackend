package com.raos.dao;

import java.util.List;

import org.springframework.stereotype.Component;
import com.raos.model.Customer;
import com.raos.model.CustomerAddress;
import com.raos.model.CustomerProfile;
import com.raos.request.AddAddressRequest;

@Component
public interface CustomerDao {
	
	public Customer checkCustomer(Customer customer);
	
	public int addCustomerAddress(AddAddressRequest req);
	
	public boolean updateCustomerAddress(AddAddressRequest addAddrReq, int address_id);

	public boolean deleteCustomerAddress(int address_id);

	public List<CustomerAddress> getCustomerAddress(int customer_id);
	
	public CustomerProfile getCustomerProfile(int customer_id);

	boolean updateCustomerProfile(CustomerProfile custProf);

	public boolean checkDeliveryAvailability(String pinCode);

	}
