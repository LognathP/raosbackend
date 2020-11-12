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
import com.raos.constants.LoginQueryConstants;
import com.raos.logger.CommonLogger;
import com.raos.model.Customer;


@Component
public class CustomerDaoImpl implements CustomerDao {
	
	@Autowired
	CommonLogger LOGGER;
	
	@Autowired
	JdbcTemplate jdbctemp;
	
	@Override
	public Customer checkCustomer(Customer customer) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(CustomerQueryConstants.LOGIN_CUSTOMER_CHECK);
			preStmt.setString(1, customer.getMobileno());
			res = preStmt.executeQuery();
			if (res.next()) {
				customer.setId(res.getLong(1));
				customer.setMembership_flag(res.getInt(2));
				LOGGER.info(this.getClass(), "CUSTOMER ALREADY EXISTS - ID:" + customer.getId());
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE checkUser " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON checkUser " + e.getMessage());
			}

		}
		return customer;
	}

	
	
}
