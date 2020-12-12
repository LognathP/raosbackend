package com.raos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.raos.constants.CustomerQueryConstants;
import com.raos.logger.CommonLogger;
import com.raos.model.Customer;
import com.raos.model.CustomerAddress;
import com.raos.model.CustomerProfile;
import com.raos.request.AddAddressRequest;


@Component
public class CustomerDaoImpl implements CustomerDao {
	
	@Autowired
	CommonLogger LOGGER;
	
	@Autowired
	JdbcTemplate jdbctemp;
	
	@Override
	public Customer checkCustomer(Customer customer) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(CustomerQueryConstants.LOGIN_CUSTOMER_CHECK);
			preStmt.setString(1, customer.getMobileno());
			res = preStmt.executeQuery();
			if (res.next()) {
				customer.setId(res.getLong(1));
				customer.setMembership_flag(res.getInt(2));
				LOGGER.info(this.getClass(), "CUSTOMER ALREADY EXISTS - ID:" + customer.getId());
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE checkUser " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON checkUser " + e.getMessage());
			}

		}
		return customer;
	}

	@Override
	public int addCustomerAddress(AddAddressRequest req) {
		PreparedStatement preStmt = null;
		Connection connection = null;
		ResultSet res = null;
		int id = 0;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(CustomerQueryConstants.CUSOMER_ADDRESS_INSERT,new String[] {"id"});
			preStmt.setInt(1, req.getCustomer_id());
			preStmt.setString(2, req.getAddress_type());
			preStmt.setString(3,req.getDoor_no());
			preStmt.setString(4,req.getStreet_name());
			preStmt.setString(5,req.getPincode());
			preStmt.setString(6, req.getCity());
			preStmt.setString(7, req.getState());
			preStmt.setString(8, req.getCountry());
			preStmt.setString(9, req.getLatitude());
			preStmt.setString(10, req.getLongitude());
			preStmt.executeUpdate();
			if(preStmt.executeUpdate() > 0)
			{
				res = preStmt.getGeneratedKeys();
				if(res.next())
				{
					id = res.getInt(1);
				}
			}
		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE addCustomerAddress " + e.getMessage());
			e.printStackTrace();
		} 
		finally {
			try {
				res.close();
				preStmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB addCustomerAddress " + e.getMessage());
			}

		}
		return id;
	}

	@Override
	public boolean updateCustomerAddress(AddAddressRequest req, int address_id) {
		PreparedStatement preStmt = null;
		Connection connection = null;
		boolean stat = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(CustomerQueryConstants.CUSTOMER_ADDRESS_UPDATE);
			preStmt.setInt(1, req.getCustomer_id());
			preStmt.setString(2, req.getAddress_type());
			preStmt.setString(3,req.getDoor_no());
			preStmt.setString(4,req.getStreet_name());
			preStmt.setString(5,req.getPincode());
			preStmt.setString(6, req.getCity());
			preStmt.setString(7, req.getState());
			preStmt.setString(8, req.getCountry());
			preStmt.setString(9, req.getLatitude());
			preStmt.setString(10, req.getLongitude());
			preStmt.setInt(11, address_id);
			preStmt.executeUpdate();
			if(preStmt.executeUpdate() > 0)
			{
				stat = true;
			}
		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE updateCustomerAddress " + e.getMessage());
			e.printStackTrace();
		} 
		finally {
			try {
				preStmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB updateCustomerAddress " + e.getMessage());
			}

		}
		return stat;
	}
	
	@Override
	public boolean deleteCustomerAddress(int address_id) {
		PreparedStatement preStmt = null;
		Connection connection = null;
		boolean stat = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(CustomerQueryConstants.CUSTOMER_ADDRESS_DELETE);
			preStmt.setInt(1, address_id);
			preStmt.executeUpdate();
			if(preStmt.executeUpdate() > 0)
			{
				stat = true;
			}
		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE deleteCustomerAddress " + e.getMessage());
			e.printStackTrace();
		} 
		finally {
			try {
				preStmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB deleteCustomerAddress " + e.getMessage());
			}

		}
		return stat;
	}
	
	@Override
	public List<CustomerAddress>  getCustomerAddress(int customerId) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		
		List<CustomerAddress> custAddList = new ArrayList<>();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(CustomerQueryConstants.GET_CUSTOMER_ADDRESS);
			preStmt.setInt(1, customerId);
			res = preStmt.executeQuery();
			while(res.next()) {
				CustomerAddress custAddress = new CustomerAddress();
				custAddress.setId(res.getInt(1));
				custAddress.setCustomer_id(res.getInt(2));
				custAddress.setAddress_type(res.getString(3));
				custAddress.setDoor_no(res.getString(4));
				custAddress.setStreet_name(res.getString(5));
				custAddress.setCity(res.getString(6));
				custAddress.setPincode(res.getString(7));
				custAddress.setState(res.getString(8));
				custAddress.setCountry(res.getString(9));
				custAddress.setLatitude(res.getString(10));
				custAddress.setLongitude(res.getString(11));
				custAddress.setCreated(res.getString(12));
				custAddress.setUpdated(res.getString(13));
				custAddList.add(custAddress);
				
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getCustomerAddress " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON getCustomerAddress " + e.getMessage());
			}

		}
		return custAddList;
	}
	
	

	@Override
	public CustomerProfile getCustomerProfile(int customer_id) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		CustomerProfile cpf = new CustomerProfile();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(CustomerQueryConstants.GET_CUSTOMER_PROFILE);
			preStmt.setInt(1, customer_id);
			res = preStmt.executeQuery();
			while(res.next()) {
				cpf.setCustomer_firstname(res.getString(1));
				cpf.setCustomer_lastname(res.getString(2));
				cpf.setCustomer_dob(res.getDate(3));
				cpf.setCustomers_email_address(res.getString(4));
				cpf.setCustomers_telephone(res.getString(5));
				cpf.setCustomer_id(customer_id);
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getCustomerProfile " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON getCustomerProfile " + e.getMessage());
			}

		}
		return cpf;
	}
	
	@Override
	public boolean updateCustomerProfile(CustomerProfile custProf) {
		PreparedStatement preStmt = null;
		Connection connection = null;
		boolean stat = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(CustomerQueryConstants.UPDATED_CUSTOMER_PROFILE);
			preStmt.setString(1, custProf.getCustomer_firstname());
			preStmt.setString(2, custProf.getCustomer_lastname());
			preStmt.setDate(3, custProf.getCustomer_dob());
			preStmt.setString(4, custProf.getCustomers_email_address());
			preStmt.setString(5, custProf.getCustomers_telephone());
			preStmt.setInt(6, custProf.getCustomer_id());
			preStmt.executeUpdate();
			if(preStmt.executeUpdate() > 0)
			{
				stat = true;
			}
		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE updateCustomerProfile " + e.getMessage());
			e.printStackTrace();
		} 
		finally {
			try {
				preStmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB updateCustomerProfile " + e.getMessage());
			}

		}
		return stat;
	}


	@Override
	public boolean checkDeliveryAvailability(String pinCode) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		boolean stat = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(CustomerQueryConstants.PINCODE_CHECK);
			preStmt.setString(1, pinCode);
			res = preStmt.executeQuery();
			while(res.next()) {
				if(res.getInt(1)>0)
				{
					stat = true;
				}
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE checkDeliveryAvailability " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON checkDeliveryAvailability " + e.getMessage());
			}

		}
		return stat;
	}
	
	
	
	
}
