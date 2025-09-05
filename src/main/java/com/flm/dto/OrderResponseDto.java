package com.flm.dto;

import java.util.List;

import com.flm.model.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
	
	private long orderId;
	
	private String status;
	
	private double totalPrice;
	
	private List<OrderItemResponseDto> orderItems;

}
