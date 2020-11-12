package com.raos.constants;

public interface OrderQueryConstants {
	
	
	String GET_CUSTOMER_ORDERS  = "select o.order_id,o.order_total,o.order_grand_total,o.order_status from orders o,customers c where o.customer_id = c.id and c.id = ?";
	
	String GET_ORDER_ITEMS_BYID= "select oi.item_quantity ,oi.item_total_price from orders o,order_items oi,products p where o.order_id = oi.order_id \r\n" + 
			"and oi.product_id = p.id and o.order_id = ?";
	
	String INSERT_ORDER = "insert into orders (order_id,customer_id,order_total,order_grand_total,order_status,created,updated) values"
			+ " (nextval('seq_order_id'),?,?,?,1,current_timestamp,current_timestamp) RETURNING order_id";
	

	String INSERT_ORDER_ITEM = "insert into order_items (order_item_id,order_id,item_quantity,item_total_price,"
			+ "product_id,created,updated) values"
			+ " (nextval('seq_orderitem_id'),?,?,?,?,current_date,current_date)";
	
	}
