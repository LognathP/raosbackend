package com.raos.dao;

import java.util.List;

import org.springframework.stereotype.Component;
import com.raos.model.Customer;
import com.raos.model.CustomerAddress;
import com.raos.model.CustomerProfile;
import com.raos.model.OrderDetails;
import com.raos.model.Product;
import com.raos.request.AddAddressRequest;
import com.raos.request.OrderFilter;
import com.raos.request.OrderRequest;

@Component
public interface OrderDao {
	
	public boolean placeOrder(OrderRequest orderReq);

	public boolean updateOrderStatus(int orderId, int orderStatus);

	public List<OrderDetails> getOrderDetails(OrderFilter orderFilter);
	
	}
