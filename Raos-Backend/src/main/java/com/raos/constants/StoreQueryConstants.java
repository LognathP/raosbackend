package com.raos.constants;

public interface StoreQueryConstants {
	
	
	String GET_DELIVERY_SLOTS  = "select id,instant_delivery,day,delivery_slots,status from delivery_details";
	
	String UPDATE_STORE_SLOTS = "update delivery_details set day=?,delivery_slots=?,status=?,instant_delivery=? where id = ?";
	
	String UPDATE_STORE_PRODUCT = "update products set product_name=?,image_url=?,discount_flag=?,status=? where product_id=?";
	
	String DELETE_STORE_PRODUCT = "delete from products where product_id=?";

	String GET_ALL_PRODUCTS = "select p.product_id,p.product_name,c.category_name,s.subcategory_name,p.status,p.discount_flag from products p,category c,subcategory s where p.category = c.category_id and p.subcategory = s.subcategory_id";
	
	String GET_ALL_OFFERS = "select offer_id,offer_name,offer_code,offer_expiry,offer_status from offers";
	
	String ADD_OFFERS = "insert into offers (offer_id,offer_name,offer_code,offer_expiry,created,updated) values (nextval('seq_offer_id'),?,?,?,current_date,current_date)";
	
	String UPDATE_OFFER = "update offers set offer_name=?,offer_code=?,offer_expiry=?,offer_status=? where offer_id=?";
	
	String DELETE_OFFER = "delete from offers where offer_id=?";

}
