package com.raos.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.raos.dao.StoreDao;
import com.raos.logger.CommonLogger;
import com.raos.model.Offers;
import com.raos.model.StoreDelivery;
import com.raos.model.StoreProducts;

@Component
public class StoreServiceImpl implements StoreService {

	@Autowired
	CommonLogger LOGGER;
	
	@Autowired
	StoreDao storeDao;


	@Override
	public List<StoreDelivery> getDelivery() {
		return storeDao.getDelivery();
	}


	@Override
	public boolean updateDeliveryDetails(StoreDelivery slotsRequests) {
		return storeDao.updateDeliveryDetails(slotsRequests);
	}


	@Override
	public List<StoreProducts> listProducts() {
		return storeDao.listProducts();
	}


	@Override
	public boolean editProduct(StoreProducts sp) {
		return storeDao.editProduct(sp);
	}


	@Override
	public boolean deleteProduct(int product_id) {
		return storeDao.deleteProduct(product_id);
	}


	@Override
	public List<Offers> listOffers() {
		return storeDao.listOffers();
	}


	@Override
	public boolean addOffer(Offers off) {
		return storeDao.addOffer(off);
	}


	@Override
	public boolean editOffer(Offers off) {
		return storeDao.editOffer(off);
	}


	@Override
	public boolean deleteOffer(int offer_id) {
		return storeDao.deleteOffer(offer_id);
	}
		



	

}
