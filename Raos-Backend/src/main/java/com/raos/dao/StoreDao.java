package com.raos.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.raos.model.Offers;
import com.raos.model.StoreDelivery;
import com.raos.model.StoreProducts;

@Component
public interface StoreDao {
	
	public List<StoreDelivery> getDelivery();

	public boolean updateDeliveryDetails(StoreDelivery deliveryDetails);

	public List<StoreProducts> listProducts();

	public boolean editProduct(StoreProducts sp);

	public boolean deleteProduct(int product_id);

	public List<Offers> listOffers();

	public boolean addOffer(Offers off);

	public boolean editOffer(Offers off);

	public boolean deleteOffer(int offer_id);
	
	}
