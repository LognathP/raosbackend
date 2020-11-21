package com.raos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.raos.constants.CustomerQueryConstants;
import com.raos.constants.ProductQueryConstants;
import com.raos.logger.CommonLogger;
import com.raos.model.Customer;
import com.raos.model.CustomerAddress;
import com.raos.model.CustomerProfile;
import com.raos.model.Product;
import com.raos.request.AddAddressRequest;


@Component
public class ProductDaoImpl implements ProductDao {
	
	@Autowired
	CommonLogger LOGGER;
	
	@Autowired
	JdbcTemplate jdbctemp;
	
	
	
	@Override
	public List<Object>  getCategory() {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		
		List<Object> categ = new ArrayList<>();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(ProductQueryConstants.GET_CATEGORY);
			res = preStmt.executeQuery();
			while(res.next()) {
				Object ob [] = new Object[2];
				ob[0] = res.getString(1);
				ob[1] = res.getString(2);
				categ.add(ob);
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getCategory " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON getCategory " + e.getMessage());
			}

		}
		return categ;
	}
	
	@Override
	public List<Object> getSubCategory(int categoryId) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		
		List<Object> subCateg = new ArrayList<>();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(ProductQueryConstants.GET_SUB_CATEGORY);
			preStmt.setInt(1, categoryId);
			res = preStmt.executeQuery();
			while(res.next()) {
				Object ob [] = new Object[2];
				ob[0] = res.getString(1);
				ob[1] = res.getString(2);
				subCateg.add(ob);
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getSubCategory " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON getSubCategory " + e.getMessage());
			}

		}
		return subCateg;
	}
	
	@Override
	public List<Product> getProducts(int subCategoryId) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		
		List<Product> prodList = new ArrayList<>();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(ProductQueryConstants.GET_PRODUCTS);
			preStmt.setInt(1, subCategoryId);
			res = preStmt.executeQuery();
			while(res.next()) {
				Product p = new Product();
				p.setProduct_name(res.getString(1));
				p.setDiscount_flag(res.getInt(2));
				p.setUnit_metrics(res.getInt(3));
				p.setProduct_id(res.getInt(4));
				p.setBalance_stock(res.getInt(5));
				p.setMrp_price(res.getInt(6));
				p.setSales_price(res.getInt(7));
				prodList.add(p);
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getProducts " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON getProducts " + e.getMessage());
			}

		}
		return prodList;
	}
	


	
	
	
}
