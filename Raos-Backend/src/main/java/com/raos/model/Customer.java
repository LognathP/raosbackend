package com.raos.model;

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
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name = "customers")
public class Customer {
	
	@Id
	@SequenceGenerator(name = "deviceIdSeqGen", sequenceName = "seq_cust_id", allocationSize = 1)
	@GeneratedValue(generator = "deviceIdSeqGen")
	Long id;
   
    public String email;
    private Date dob;
    public String lastname;
    public String firstname;
    public String mobileno;
    @Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date created;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;
	
	public int membership_flag;

}
