package com.raos.test;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfile {

	public int id;
    public String customers_gender;
	public String customers_firstname;
	public String customers_lastname;
	public String customers_dob;
	public String customers_email_address;
	public String customers_telephone;
	public String customers_default_address_id;
	public String customers_fax;
	public String customers_password;
	public int customers_newsletter;
	public String otp;
	public String type;
	 public int enable;
	public int store_id;
}
