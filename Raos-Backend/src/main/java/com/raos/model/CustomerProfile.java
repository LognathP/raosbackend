package com.raos.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerProfile {

	public int customer_id;
	public String customer_firstname;
	public String customer_lastname;
	public Date customer_dob;
	public String customers_email_address;
	public String customers_telephone;
}
