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
@Table(name = "products")
public class ProductUpload {
	
	@Id
	@SequenceGenerator(name = "prodIdSeqGen", sequenceName = "seq_prod_id", allocationSize = 1)
	@GeneratedValue(generator = "prodIdSeqGen")
	private long product_id;
	
	private String product_name;
	private int category;
	private int subcategory;
	private int discount_flag;
	private int status;
	private String image_url;
	private int unit_metrics;
	
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date created;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;

}
