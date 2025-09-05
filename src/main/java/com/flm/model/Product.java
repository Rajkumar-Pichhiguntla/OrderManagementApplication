package com.flm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long productId;
	
	@Column(nullable = false,unique = true)
	private String productName;
	
	@Column(nullable = false)
	private double price;
	
	private double discount;
	
	private double rating;
	
	@Column(nullable = false)
	private boolean isAvailable;
	
	@Column(nullable = false)
	private long stock;
	
	
	public Product(String productName, double price, double discount, double rating, boolean isAvailable, long stock) {
		super();
		this.productName = productName;
		this.price = price;
		this.discount = discount;
		this.rating = rating;
		this.isAvailable = isAvailable;
		this.stock = stock;
	}
	

}
