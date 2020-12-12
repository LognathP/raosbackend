package com.raos.request;

import java.util.List;

import com.raos.model.DeliveryTimeSlot;
import com.raos.model.OrderDelivery;
import com.raos.model.OrderItems;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {

	int customer_id;
	double order_total;
	double order_grand_total;
	int voucher_id;
	List<OrderItems> order_items;
	OrderDelivery orderDelivery;
	DeliveryTimeSlot deliveryTimeSlot;
}
