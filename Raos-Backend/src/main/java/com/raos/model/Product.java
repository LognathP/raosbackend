package com.raos.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

	int product_id;
	String product_name;
	int discount_flag;
	int unit_metrics;
	int mrp_price;
	int sales_price;
	int balance_stock;
	
}
