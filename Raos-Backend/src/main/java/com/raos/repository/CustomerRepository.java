package com.raos.repository;

import org.springframework.data.repository.CrudRepository;

import com.raos.model.Customer;


public interface CustomerRepository extends CrudRepository<Customer, Long>{

}
