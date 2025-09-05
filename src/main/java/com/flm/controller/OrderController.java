package com.flm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flm.dto.OrderRequestDto;
import com.flm.dto.OrderResponseDto;
import com.flm.dto.ProductResponseDto;
import com.flm.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/buy")
	public OrderResponseDto placeOrder(@RequestBody List<OrderRequestDto> orderRequestDtos) {
		OrderResponseDto orderResponseDto = orderService.placeOrder(orderRequestDtos);
		return orderResponseDto;
	}
	
	@GetMapping("/{orderId}")
	 public ResponseEntity<OrderResponseDto> getOrderInfo(@PathVariable(name="orderId") long orderId) {
		ResponseEntity<OrderResponseDto> orderInfo = orderService.getOrderInfo(orderId);
		return orderInfo;
		 
	 }
	
	@DeleteMapping("/cancel")
	public ResponseEntity<Void> cancelItem(@RequestParam(name="orderItemId") long orderItemId) {
		return orderService.cancelOrderItem(orderItemId); 
	}
	
	@DeleteMapping("/cancelOrder/{orderId}")
	public ResponseEntity<Void>  cancelOrder(@PathVariable(name="orderId") long orderId) {
		orderService.cancelOrder(orderId);
		return null;
		
	}
}
