package com.raos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.raos.constants.CommonConstants;
import com.raos.constants.OrderQueryConstants;
import com.raos.logger.CommonLogger;
import com.raos.model.DeliveryTimeSlot;
import com.raos.model.OrderDelivery;
import com.raos.model.OrderDetails;
import com.raos.model.OrderItems;
import com.raos.model.OrderItemsDetails;
import com.raos.request.OrderFilter;
import com.raos.request.OrderRequest;


@Component
public class OrderDaoImpl implements OrderDao {
	
	@Autowired
	CommonLogger LOGGER;
	
	@Autowired
	JdbcTemplate jdbctemp;
	
	
	

	@Override
	public boolean placeOrder(OrderRequest orderReq) {
		Connection connection = null;

		int itemCount = 0;
		int itemTableInsrt = 0;
		boolean flag = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			connection.setAutoCommit(false);
			int orderId = insertOrderTable(connection,orderReq);
			if(orderId>0)
			{
				if(orderReq.getVoucher_id()!=0)
				{
					insertVoucherUsageTable(connection, orderReq, orderId);
				}
				List<OrderItems> olist = orderReq.getOrder_items();
				for (OrderItems orderItemsRequest : olist) {
					itemTableInsrt = insertOrderItemTable(connection, orderReq, orderId,orderItemsRequest);
					itemCount = itemCount+itemTableInsrt;
				}
				if(olist.size()==itemCount)
				{
					if(insertOrderDeliveryTable(connection, orderReq, orderId)>0)
					{
						flag = true;
					}
				}
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE placeOrder " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if(flag)
				connection.commit();
				else
				connection.rollback();
				
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB placeOrder " + e.getMessage());
			}

		}
		return flag;
	}
	
	@SuppressWarnings("unchecked")
	public int insertOrderTable(Connection connection,OrderRequest orderReq)
	{
		PreparedStatement preStmt = null;
		int insert = 0;
		ResultSet res = null;
		int id = 0;
			JSONObject jo = new JSONObject();
			jo.put("from", orderReq.getDeliveryTimeSlot().getFrom());
			jo.put("to",orderReq.getDeliveryTimeSlot().getTo());
		
		try {
			preStmt = connection.prepareStatement(OrderQueryConstants.INSERT_ORDER_TABLE,new String[] {"order_id"});
			preStmt.setInt(1, orderReq.getCustomer_id());
			preStmt.setDouble(2, orderReq.getOrder_total());
			preStmt.setDouble(3, orderReq.getOrder_grand_total());
			preStmt.setInt(4, CommonConstants.ORDER_INITIATED);
			preStmt.setInt(5, orderReq.getVoucher_id());
			preStmt.setString(6, jo.toString());
			insert = preStmt.executeUpdate();
			if(insert > 0)
			{
				res = preStmt.getGeneratedKeys();
				if(res.next())
				{
					id = res.getInt(1);
				}
				
			}
		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE insertOrderTable " + e.getMessage());
			e.printStackTrace();
		} 
		finally {
			try {
				res.close();
				preStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB insertOrderTable " + e.getMessage());
			}

		}
		return id;
	}
	public int insertOrderItemTable(Connection connection,OrderRequest orderReq,int OrderId,OrderItems oiReq)
	{
		PreparedStatement preStmt = null;
		int insert = 0;
		try {
			preStmt = connection.prepareStatement(OrderQueryConstants.INSERT_ORDER_ITEMS_TABLE);
			preStmt.setInt(1, OrderId);
			preStmt.setInt(2, oiReq.getItem_quantity());
			preStmt.setDouble(3, oiReq.getItem_total_price());
			preStmt.setInt(4, oiReq.getProduct_id());
			insert = preStmt.executeUpdate();
			
		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE insertOrderItemTable " + e.getMessage());
			e.printStackTrace();
		} 
		finally {
			try {
				preStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB insertOrderItemTable " + e.getMessage());
			}

		}
		return insert;
	}
	@SuppressWarnings("unchecked")
	public int insertOrderDeliveryTable(Connection connection,OrderRequest orderReq,int orderId)
	{
		PreparedStatement preStmt = null;
		int insert = 0;
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("name", orderReq.getOrderDelivery().getName());
		jo.put("lat", orderReq.getOrderDelivery().getLat());
		jo.put("lng", orderReq.getOrderDelivery().getLng());
		ja.add(jo);
		try {
			
			preStmt = connection.prepareStatement(OrderQueryConstants.INSERT_ORDER_DELIVERY_TABLE);
			preStmt.setInt(1, orderReq.getOrderDelivery().getAddress_type());
			preStmt.setString(2, ja.toString());
			preStmt.setInt(3, orderId);
			preStmt.setInt(4, orderReq.getCustomer_id());
			preStmt.setString(5, orderReq.getOrderDelivery().getPincode());
			insert = preStmt.executeUpdate();
			
		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE insertOrderDeliveryTable " + e.getMessage());
			e.printStackTrace();
		} 
		finally {
			try {
				preStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB insertOrderDeliveryTable " + e.getMessage());
			}

		}
		return insert;
	}
	
	public void insertVoucherUsageTable(Connection connection,OrderRequest orderReq,int orderId)
	{
		PreparedStatement preStmt = null;
		try {
			
			preStmt = connection.prepareStatement(OrderQueryConstants.INSERT_VOUCHER_USAGE);
			preStmt.setInt(1, orderReq.getCustomer_id());
			preStmt.setInt(2, orderReq.getVoucher_id());
			preStmt.setInt(3, orderId);
			preStmt.executeUpdate();
			
		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE insertVoucherUsageTable " + e.getMessage());
			e.printStackTrace();
		} 
		finally {
			try {
				preStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB insertVoucherUsageTable " + e.getMessage());
			}

		}
	}
	
	@Override
	public boolean updateOrderStatus(int order_id,int order_status) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(OrderQueryConstants.UPDATE_ORDER_STATUS);
			preStmt.setInt(1, order_status);
			preStmt.setInt(2, order_id);
			if (preStmt.executeUpdate()>0) {
				updStatus = true;
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE updateOrderStatus " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB updateOrderStatus " + e.getMessage());
			}

		}
		return updStatus;
	}

	@Override
	public List<OrderDetails> getOrderDetails(OrderFilter orderFilter) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		PreparedStatement preStmt2 = null;
		List<OrderDetails> ol = new ArrayList<OrderDetails>();
		ResultSet rs = null;
		ResultSet rs2 = null;
		StringBuilder queryBuilder = new StringBuilder();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			queryBuilder.append(OrderQueryConstants.GET_CUSTOMER_ORDERS);
			if(orderFilter.getFrom() !=null && orderFilter.getTo()!=null)
			{
				queryBuilder.append(" and o.created between to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.S') and to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.S')");
			}
			else
			{
				queryBuilder.append(" and o.created >=current_timestamp - interval '10 day' and o.created <=current_timestamp");
			}
			if(orderFilter.getCustomer_id()!=0)
			{
				queryBuilder.append(" and o.customer_id = "+orderFilter.getCustomer_id());
			}
			if(orderFilter.getOrder_id()!=0)
			{
				queryBuilder.append(" and o.order_id = "+orderFilter.getOrder_id());
			}
			if(orderFilter.getStatus()!=0)
			{
				queryBuilder.append(" and o.order_status = "+orderFilter.getStatus());
			}		
			preStmt = connection.prepareStatement(queryBuilder.toString());
			if(orderFilter.getFrom() !=null && orderFilter.getTo()!=null)
			{
				preStmt.setString(1, orderFilter.getFrom());
				preStmt.setString(2, orderFilter.getTo());
			}
			rs = preStmt.executeQuery();
			while(rs.next())
			{
				OrderDetails od = new OrderDetails();
				od.setOrder_id(rs.getInt("order_id"));
				od.setCustomer_id(rs.getInt("customer_id"));
				od.setOrder_status(rs.getInt("order_status"));
				od.setOrder_total(rs.getDouble("order_total"));
				od.setVoucher_id(rs.getInt("voucher_id"));
				od.setOrder_grand_total(rs.getDouble("order_grand_total"));
				od.setOrder_date(rs.getString("created"));
				OrderDelivery odl = new OrderDelivery();
				odl.setAddress_type(rs.getInt("address_type"));
				odl.setPincode(rs.getString("pincode"));
				Gson gson = new Gson(); 
				OrderDelivery[] adarray = gson.fromJson(rs.getString("address_location"), OrderDelivery[].class); 
				for(OrderDelivery df : adarray) {
					odl.setName(df.getName());
					odl.setLat(df.getLat());
					odl.setLng(df.getLng());
				}
				od.setOrderDelivery(odl);
				List<OrderItemsDetails> odList = new ArrayList<OrderItemsDetails>();
				preStmt2 = connection.prepareStatement(OrderQueryConstants.GET_ORDER_ITEMS_BYID);
				preStmt2.setInt(1, od.getOrder_id());
				rs2 = preStmt2.executeQuery();
				while (rs2.next()) {
					OrderItemsDetails ot = new OrderItemsDetails();
					ot.setItem_quantity(rs2.getInt("item_quantity"));
					ot.setItem_total_price(rs2.getDouble("item_total_price"));
					ot.setProduct_id(rs2.getInt("product_id"));
					ot.setProduct_name(rs2.getString("product_name"));
					ot.setOrder_item_id(rs2.getInt("order_item_id"));
					odList.add(ot);				
				}
				rs2.close();
				preStmt2.close();
				od.setOrder_item_details(odList);
				ol.add(od);
			}
			
			

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getOrderDetails " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				preStmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB getOrderDetails " + e.getMessage());
			}

		}
		return ol;
	}
	
	@Override
	public boolean voucherUsageCheck(int customerId, int voucherId) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		boolean stat = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
     		preStmt = connection.prepareStatement(OrderQueryConstants.VOUCHER_USAGE_QUERY);
			preStmt.setInt(1, customerId);
			preStmt.setInt(2, voucherId);
			rs = preStmt.executeQuery();
			while(rs.next())
			{
				if(rs.getInt(1)>0)
				{
					stat = true;
				}
			}
			
			

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE voucherUsageCheck " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				preStmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB voucherUsageCheck " + e.getMessage());
			}

		}
		return stat;
	}



	
	
	
}
