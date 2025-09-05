package com.flm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponseDto {
	
	private long productId;
	
	private String productName;
	
	private long quantity;
	
	private double eachProductPrice;
	
	private double totalProductPrice;

}
