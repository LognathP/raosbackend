package com.raos.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
	
    @NotEmpty(message = "Product Name cannot be null")
	private String product_name;
    @NotEmpty(message = "Category cannot be null")
    @Pattern(regexp = "^[\\d]+$",message = "Category ID should be numerics") 
    private int category;
    @NotEmpty(message = "Subcategory cannot be null")
	private int subcategory;
    @NotEmpty(message = "Discount Flag cannot be null")
	private int discount_flag;
    @NotEmpty(message = "Status cannot be null")
	private int status;
    @NotEmpty(message = "Image URL cannot be null")
	private String image_url;
	@NotEmpty(message = "Unit Metrics cannot be null")
	private int unit_metrics;
	
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date created;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;

}
