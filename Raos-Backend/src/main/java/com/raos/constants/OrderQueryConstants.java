package com.raos.constants;

public interface OrderQueryConstants {
	
	
	String GET_CUSTOMER_ORDERS  = "select o.order_id,o.customer_id,o.order_total,o.order_grand_total,o.order_status,o.offer_id,od.address_type,od.address_location,o.created from orders o,order_delivery od where o.order_id=od.order_id ";
	
	String GET_ORDER_ITEMS_BYID= "select oi.order_item_id,oi.item_total_price,oi.item_quantity,p.product_name,p.product_id from order_items oi,products p where oi.product_id = p.product_id and oi.order_id = ?";
	
	String INSERT_ORDER_TABLE = "insert into orders (order_id,customer_id,order_total,order_grand_total,order_status,created,updated) values"
			+ " (nextval('seq_order_id'),?,?,?,?,current_timestamp,current_timestamp)";
	
	String INSERT_ORDER_ITEMS_TABLE = "insert into order_items (order_item_id,order_id,item_quantity,item_total_price,"
			+ "product_id,created,updated) values"
			+ " (nextval('seq_orderitem_id'),?,?,?,?,current_date,current_date)";
	
	String INSERT_ORDER_DELIVERY_TABLE = "insert into order_delivery (id,address_type,address_location,order_id,customer_id,created,updated) values (nextval('seq_orderdelivery_id'),?,?,?,?,current_timestamp,current_timestamp)";
	
	String UPDATE_ORDER_STATUS = "update orders set order_status= ? where order_id = ?";

	
	}
