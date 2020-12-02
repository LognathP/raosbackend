package com.raos.constants;

public interface ProductQueryConstants {
	
	String GET_CATEGORY = "select category_id,category_name,category_alias_name from category";
	
	//String GET_SUB_CATEGORY = "select subcategory_id,subcategory_name from subcategory where category_id=?";
	
	String GET_PRODUCTS = "select p.product_name,p.discount_flag,p.unit_metrics,s.product_id,sum(s.balance_stock) as stock,max(s.mrp_price) as mrp_price,max(s.sales_price) as sales_price,p.image_url from stocks s,products p where p.product_id=s.product_id and (p.category_1 = ? or p.category_2 = ? or p.category_3 = ?) group by s.product_id,p.product_name,p.discount_flag,p.unit_metrics,p.image_url order by s.product_id";

	
	}
