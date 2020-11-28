package com.raos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raos.model.ProductUpload;


public interface ProductsRepository extends JpaRepository<ProductUpload,Long>{

}
