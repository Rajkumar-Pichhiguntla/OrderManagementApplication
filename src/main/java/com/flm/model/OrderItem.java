package com.flm.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="order_items")
@Entity
public class OrderItem {
	
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long orderItemId;
	
	private long quantity;
	
	
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
	
	@ManyToOne
	@JoinColumn(name="order_id")
	private Order order;
	
	public OrderItem(long quantity, Product product, Order order) {
		super();
		this.quantity = quantity;
		this.product = product;
		this.order = order;
	}
}
