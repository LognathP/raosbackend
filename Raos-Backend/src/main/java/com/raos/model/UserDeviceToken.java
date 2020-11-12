package com.raos.model;


import java.util.Date;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;

import com.raos.util.AttributeEncryptor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name = "customer_device")
public class UserDeviceToken {

	@Id
	@SequenceGenerator(name = "deviceIdSeqGen", sequenceName = "seq_device_id", allocationSize = 1)
	@GeneratedValue(generator = "deviceIdSeqGen")
	Long id;
	
	int customer_id;
	String device_token;
	int device_type;
	
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date created;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;
	
}
