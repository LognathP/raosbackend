package com.raos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.raos.model.CustomerProfile;
import com.raos.model.OrderDetails;
import com.raos.model.Product;
import com.raos.model.Customer;
import com.raos.model.CustomerAddress;
import com.raos.request.AddAddressRequest;
import com.raos.request.OrderFilter;
import com.raos.request.OrderRequest;

@Service
public interface OrderService {
	
	public boolean placeOrder(OrderRequest orderReq);

	public boolean updateOrderStatus(int orderId, int orderStatus);

	public List<OrderDetails> getOrderDetails(OrderFilter orderFilter);

}
