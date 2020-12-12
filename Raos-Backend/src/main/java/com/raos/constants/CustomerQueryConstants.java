package com.raos.constants;

public interface CustomerQueryConstants {
	
	String LOGIN_CUSTOMER_CHECK = "select id,membership_flag from customers where mobileno=?";
	
	String CUSOMER_ADDRESS_INSERT = "insert into customer_address (customer_id,address_type,door_no,street_name,pincode,city,state,country,latitude,longitude,created,updated) values\r\n" + 
			"(?,?,?,?,?,?,?,?,?,?,current_timestamp,current_timestamp)";
	
	String CUSTOMER_ADDRESS_UPDATE = "update customer_addresses set customer_id=?,address_type=?,door_no=?,street_name=?,pincode=?,"
			+ "city=?,state=?,country=?,latitude=?,longitude=?,updated=current_timestamp where id=?";
	
	String CUSTOMER_ADDRESS_DELETE = "delete from customer_address where id=?";
	
	String GET_CUSTOMER_ADDRESS = "select * from customer_address where customer_id= ?";
	
	String CUSTOMER_EXIST_CHECK_QUERY = "select count(1) from customers where id=?";
	
	String CUSTOMER_ADDRESS_EXIST_CHECK_QUERY = "select count(1) from customer_address where id=?";
	
	String GET_CUSTOMER_PROFILE = "select firstname,lastname,dob,email,mobileno from customers where id = ?";
	
	String UPDATED_CUSTOMER_PROFILE = "update customers set firstname=?,lastname=?,dob=?,email=?,mobileno=? where id = ?";
	
	String PINCODE_CHECK = "select count(1) from pincode where pincode=?";

	
	}
