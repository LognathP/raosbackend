package com.raos.business;

import java.util.List;
import java.util.Optional;

import org.checkerframework.checker.units.qual.m;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


import com.raos.logger.CommonLogger;
import com.raos.logger.ConfigProperties;
import com.raos.model.CommonDataResponse;
import com.raos.model.CommonResponse;
import com.raos.model.CommonSingleResponse;
import com.raos.model.CustomerAddress;
import com.raos.model.CustomerProfile;
import com.raos.model.OrderDetails;
import com.raos.model.Product;
import com.raos.request.AddAddressRequest;
import com.raos.request.OrderFilter;
import com.raos.request.OrderRequest;
import com.raos.service.CustomerService;
import com.raos.service.OrderService;
import com.raos.service.ProductService;

import io.swagger.v3.core.util.Json;

@Component
public class OrderBusiness {

	@Autowired
	CommonLogger LOGGER;

	@Autowired
	ConfigProperties configProp;

	@Autowired
	CommonResponse commonResponse;

	@Autowired
	OrderService orderService;

	@Autowired
	CommonDataResponse commonDataResponse;

	@Autowired
	CommonSingleResponse commonSingleResponse;


	public Object placeOrder(OrderRequest orderReq) {
		boolean voucherUsage = false;
		if(orderReq.getVoucher_id()!=0)
		{
			voucherUsage = orderService.voucherUsageCheck(orderReq.getCustomer_id(), orderReq.getVoucher_id());
		}
		if(!voucherUsage)
		{
			if (orderService.placeOrder(orderReq)) {
				LOGGER.info(this.getClass(), "ORDER PLACED SUCCESSFULLY");
				commonResponse.setStatus(HttpStatus.OK.toString());
				commonResponse.setMessage("Order Placed Successfully");
				return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
			} else {
				LOGGER.error(this.getClass(), "UNABLE TO PLACE ORDER");
				commonResponse.setStatus(HttpStatus.NOT_FOUND.toString());
				commonResponse.setMessage("Unable to Place Order");
				return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
			}
		}
		else
		{
			LOGGER.error(this.getClass(), "VOUCHER ALREADY USED");
			commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			commonResponse.setMessage("Voucher Already Used");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		}
		
		
	}

	public Object updateOrderStatus(int orderId,int orderStatus) {
		if (orderService.updateOrderStatus(orderId,orderStatus)) {
			LOGGER.info(this.getClass(), "ORDER UPDATED SUCCESSFULLY");
			commonResponse.setStatus(HttpStatus.OK.toString());
			commonResponse.setMessage("Order Updated Successfully");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		} else {
			LOGGER.error(this.getClass(), "UNABLE TO UPDATE ORDER");
			commonResponse.setStatus(HttpStatus.NOT_FOUND.toString());
			commonResponse.setMessage("Unable to Update Order");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		}
	}

	public Object getOrderDetails(OrderFilter orderFilter) {
		List<OrderDetails> oList =  orderService.getOrderDetails(orderFilter);
		if (oList.size() > 0) {
			LOGGER.info(this.getClass(), "ORDERS LISTED SUCCESSFULLY");
			commonSingleResponse.setStatus(HttpStatus.OK.toString());
			commonSingleResponse.setMessage("Orders Listed Successfully");
			commonSingleResponse.setData(oList);
			return new ResponseEntity<CommonSingleResponse>(commonSingleResponse, HttpStatus.OK);
		} else {
			LOGGER.error(this.getClass(), "NO ORDERS FOUND");
			commonResponse.setStatus(HttpStatus.NOT_FOUND.toString());
			commonResponse.setMessage("No Orders found");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		}
	}

	
}
