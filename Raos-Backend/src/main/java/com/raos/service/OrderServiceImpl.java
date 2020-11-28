package com.raos.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.raos.dao.CustomerDao;
import com.raos.dao.OrderDao;
import com.raos.dao.ProductDao;
import com.raos.logger.CommonLogger;
import com.raos.model.Customer;
import com.raos.model.CustomerAddress;
import com.raos.model.CustomerProfile;
import com.raos.model.OrderDetails;
import com.raos.model.Product;
import com.raos.repository.CustomerRepository;
import com.raos.request.AddAddressRequest;
import com.raos.request.OrderFilter;
import com.raos.request.OrderRequest;

@Component
public class OrderServiceImpl implements OrderService {

	@Autowired
	CommonLogger LOGGER;
	
	@Autowired
	OrderDao orderDao;


	@Override
	public boolean placeOrder(OrderRequest orderReq) {
		return orderDao.placeOrder(orderReq);
	}

	@Override
	public boolean updateOrderStatus(int orderId, int orderStatus) {
		return orderDao.updateOrderStatus(orderId,orderStatus);
	}

	@Override
	public List<OrderDetails> getOrderDetails(OrderFilter orderFilter) {
		return orderDao.getOrderDetails(orderFilter);
	}
		



	

}
