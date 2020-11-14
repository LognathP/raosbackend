package com.raos.model;

import lombok.Setter;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;

@Getter
@Setter
public class CustomerAddress {

	int id;
	public int customer_id;
	public String address_type;
	public String door_no;
	public String street_name;
	public String city;
	public String pincode;
	public String state;
	public String country;
	public String latitude;
	public String longitude;
	private String created;
	private String updated;
}
