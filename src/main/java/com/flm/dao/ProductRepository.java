package com.flm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flm.dto.ProductResponseDto;
import com.flm.model.Product;

import jakarta.transaction.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	
	Product findByProductId(long productId);
	
	List<Product> findByProductNameContaining(String productName);
	
	
	@Query("update Product p set p.stock=:stock where p.productId=:productId")
	@Modifying
	@Transactional
	void updateStock(@Param(value="productId") long productId,@Param(value = "stock") long stock);
	
	

}
