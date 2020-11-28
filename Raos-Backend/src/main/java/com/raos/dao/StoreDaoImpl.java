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
import com.raos.constants.ProductQueryConstants;
import com.raos.constants.StoreQueryConstants;
import com.raos.logger.CommonLogger;
import com.raos.model.DeliveryTimeSlot;
import com.raos.model.Offers;
import com.raos.model.Product;
import com.raos.model.StoreDelivery;
import com.raos.model.StoreProducts;


@Component
public class StoreDaoImpl implements StoreDao {
	
	@Autowired
	CommonLogger LOGGER;
	
	@Autowired
	JdbcTemplate jdbctemp;
	
	
	
	@Override
	public List<StoreDelivery> getDelivery() {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		List<StoreDelivery> stList = new ArrayList<StoreDelivery>();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.GET_DELIVERY_SLOTS);
			rs = preStmt.executeQuery();
			while(rs.next())
			{
				StoreDelivery sd = new StoreDelivery();
				sd.setId(rs.getInt("id"));
				sd.setInstant_delivery(rs.getInt("instant_delivery"));
				sd.setDay(rs.getString("day"));
				sd.setStatus(rs.getInt("status"));
				Gson gson = new Gson(); 
				List<DeliveryTimeSlot> dtsList = new ArrayList<DeliveryTimeSlot>();
				DeliveryTimeSlot dts = new DeliveryTimeSlot();
				DeliveryTimeSlot[] adarray = gson.fromJson(rs.getString("delivery_slots"), DeliveryTimeSlot[].class); 
				for(DeliveryTimeSlot df : adarray) {
					dts.setFrom(df.getFrom());
					dts.setTo(df.getTo());
					dtsList.add(dts);
				}
				sd.setSlots(dtsList);
				stList.add(sd);
			}
			
			

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getDelivery " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				preStmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB getDelivery " + e.getMessage());
			}

		}
		return stList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean updateDeliveryDetails(StoreDelivery deliveryDetails) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		JSONArray ja = new JSONArray();
		for (DeliveryTimeSlot slots : deliveryDetails.getSlots()) {
			JSONObject jo = new JSONObject();
			jo.put("from", slots.getFrom());
			jo.put("to",slots.getTo());
			ja.add(jo);
		}
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.UPDATE_STORE_SLOTS);
			preStmt.setString(1, deliveryDetails.getDay());
			preStmt.setString(2, ja.toString());
			preStmt.setInt(3, deliveryDetails.getStatus());
			preStmt.setInt(4, deliveryDetails.getInstant_delivery());
			preStmt.setInt(5, deliveryDetails.getId());
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
	public List<StoreProducts> listProducts() {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		
		List<StoreProducts> prodList = new ArrayList<>();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.GET_ALL_PRODUCTS);
			res = preStmt.executeQuery();
			while(res.next()) {
				StoreProducts sp = new StoreProducts();
				sp.setProduct_id(res.getInt(1));
				sp.setProduct_name(res.getString(2));
				sp.setCategory_name(res.getString(3));
				sp.setSubCategory_name(res.getString(4));
				sp.setStatus(res.getInt(5));
				sp.setDiscount_flag(res.getInt(6));
				prodList.add(sp);
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE listProducts " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON listProducts " + e.getMessage());
			}

		}
		return prodList;
	}

	@Override
	public boolean editProduct(StoreProducts sp) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.UPDATE_STORE_PRODUCT);
			preStmt.setString(1, sp.getProduct_name());
			preStmt.setString(2, sp.getImgUrl());
			preStmt.setInt(3, sp.getDiscount_flag());
			preStmt.setInt(4, sp.getStatus());
			preStmt.setInt(5, sp.getProduct_id());
			if (preStmt.executeUpdate()>0) {
				updStatus = true;
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE editProduct " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB editProduct " + e.getMessage());
			}

		}
		return updStatus;
	}
	
	@Override
	public boolean deleteProduct(int product_id) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.DELETE_STORE_PRODUCT);
			preStmt.setInt(1, product_id);
			if (preStmt.executeUpdate()>0) {
				updStatus = true;
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE deleteProduct " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB deleteProduct " + e.getMessage());
			}

		}
		return updStatus;
	}
	
	@Override
	public List<Offers> listOffers() {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		
		List<Offers> offList = new ArrayList<>();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.GET_ALL_OFFERS);
			res = preStmt.executeQuery();
			while(res.next()) {
				Offers off = new Offers();
				off.setOffer_id(res.getInt(1));
				off.setOffer_name(res.getString(2));
				off.setOffer_code(res.getString(3));
				off.setOffer_expiry(res.getDate(4));
				off.setOffer_status(res.getInt(5));
				offList.add(off);
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE listProducts " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON listProducts " + e.getMessage());
			}

		}
		return offList;
	}

	@Override
	public boolean addOffer(Offers off) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.ADD_OFFERS);
			preStmt.setString(1, off.getOffer_name());
			preStmt.setString(2, off.getOffer_code());
			preStmt.setDate(3, off.getOffer_expiry());
			if (preStmt.executeUpdate()>0) {
				updStatus = true;
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE addOffer " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB addOffer " + e.getMessage());
			}

		}
		return updStatus;
	}
	
	@Override
	public boolean editOffer(Offers off) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.UPDATE_OFFER);
			preStmt.setString(1, off.getOffer_name());
			preStmt.setString(2, off.getOffer_code());
			preStmt.setDate(3, off.getOffer_expiry());
			preStmt.setInt(4, off.getOffer_status());
			preStmt.setInt(5, off.getOffer_id());
			if (preStmt.executeUpdate()>0) {
				updStatus = true;
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE editOffer " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB editOffer " + e.getMessage());
			}

		}
		return updStatus;
	}
	
	@Override
	public boolean deleteOffer(int offer_id){
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.DELETE_OFFER);
			preStmt.setInt(1, offer_id);
			if (preStmt.executeUpdate()>0) {
				updStatus = true;
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE deleteOffer " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB deleteOffer " + e.getMessage());
			}

		}
		return updStatus;
	}
	
	
	
	
}
