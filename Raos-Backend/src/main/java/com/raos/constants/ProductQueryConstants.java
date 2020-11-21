package com.raos.constants;

public interface ProductQueryConstants {
	
	String GET_CATEGORY = "select category_id,category_name from category";
	
	String GET_SUB_CATEGORY = "select subcategory_id,subcategory_name from subcategory where category_id=?";
	
	String GET_PRODUCTS = "select p.product_name,p.discount_flag,p.unit_metrics,s.product_id,sum(s.balance_stock) as stock,max(s.mrp_price) as mrp_price,max(s.sales_price) as sales_price from stocks s,products p where p.product_id=s.product_id and p.subcategory = ? group by s.product_id,p.product_name,p.discount_flag,p.unit_metrics order by s.product_id;";

	
	}
