package com.flm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flm.dao.OrderItemRepository;
import com.flm.dao.OrderRepository;
import com.flm.dao.ProductRepository;
import com.flm.dto.OrderItemResponseDto;
import com.flm.dto.OrderRequestDto;
import com.flm.dto.OrderResponseDto;
import com.flm.exceptions.OrderItemNotFoundException;
import com.flm.exceptions.OrderNotFoundException;
import com.flm.model.Order;
import com.flm.model.OrderItem;
import com.flm.model.Product;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;

	//placeOrder
	@Override
	public OrderResponseDto placeOrder(List<OrderRequestDto> orderRequestDtos) {
		
		Order order=new Order();
		List<OrderItem> orderItems=new ArrayList<OrderItem>();
		
		List<OrderItemResponseDto> orderItemResponseDtos=new ArrayList<OrderItemResponseDto>();
		
		for(OrderRequestDto orderRequestDto:orderRequestDtos) {
			OrderItem orderItem=new OrderItem();
			Product product = productRepository.findById(orderRequestDto.getProductId()).get();
			if(product.getStock()>=orderRequestDto.getQuantity()) {
				order.setStatus("Ordered");
				orderItem.setQuantity(orderRequestDto.getQuantity());
				orderItem.setProduct(product);
				orderItem.setOrder(order);
				orderItems.add(orderItem);
				productRepository.updateStock(product.getProductId(), product.getStock()- orderRequestDto.getQuantity());
			}	
		}
		order.setOrderItems(orderItems);
		
		Order savedOrder = orderRepository.save(order);
		
		OrderResponseDto orderResponseDto = buildOrderResponseDtoFromOrder(savedOrder);
		
		return orderResponseDto;
	}
	
	//GetOrderInfo
	@Override
	public ResponseEntity<OrderResponseDto> getOrderInfo(long orderId) {
		Order order = orderRepository.findById(orderId)
									.orElseThrow(()-> new OrderNotFoundException("Order Not Found Id:"+orderId));
		OrderResponseDto orderResponseDtoFromOrder = buildOrderResponseDtoFromOrder(order);
		return ResponseEntity.status(HttpStatus.OK).body(orderResponseDtoFromOrder);
	}

	//Cancel Order Item
	@Override
	public ResponseEntity<Void> cancelOrderItem(long orderItemId) {
		OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(()-> new OrderItemNotFoundException("OrderItem Not Found with Id:"+orderItemId));
		
		orderItemRepository.delete(orderItem);
		List<OrderItem> orderItems = orderItem.getOrder().getOrderItems();
		if(orderItems.isEmpty()) {
			orderRepository.delete(orderItem.getOrder());
		}
		long productId = orderItem.getProduct().getProductId();
		long stock = orderItem.getProduct().getStock();
		productRepository.updateStock(productId, stock+orderItem.getQuantity());
		return ResponseEntity.noContent().build() ;
	}
	
	//Cancel Order
	@Override
	public ResponseEntity<Void> cancelOrder(long orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(()-> new OrderNotFoundException("Order Not Found Id:"+orderId));
		orderRepository.delete(order);
		return ResponseEntity.noContent().build();
	}
	
	
	//Extracted method
	private OrderResponseDto buildOrderResponseDtoFromOrder(Order savedOrder) {
		OrderResponseDto orderResponseDto=new OrderResponseDto();
		orderResponseDto.setOrderId(savedOrder.getOrderId());
		orderResponseDto.setStatus(savedOrder.getStatus());
		
		List<OrderItemResponseDto> orderItemResponseDtoList=new ArrayList<OrderItemResponseDto>(); 
		double totalOrderPrice=0;
		for(OrderItem orderItem:savedOrder.getOrderItems()) {
			OrderItemResponseDto orderItemResponseDto=new OrderItemResponseDto();
			
			orderItemResponseDto.setProductId(orderItem.getProduct().getProductId());
			orderItemResponseDto.setProductName(orderItem.getProduct().getProductName());
			orderItemResponseDto.setQuantity(orderItem.getQuantity());
			double discount=(orderItem.getProduct().getPrice()*orderItem.getProduct().getDiscount())/100;
			orderItemResponseDto.setEachProductPrice(orderItem.getProduct().getPrice()- discount);
			double totalProductPrice= orderItem.getQuantity() * orderItemResponseDto.getEachProductPrice();
			orderItemResponseDto.setTotalProductPrice(totalProductPrice);
			totalOrderPrice+=totalProductPrice;
			orderItemResponseDtoList.add(orderItemResponseDto);
		}
		
		orderResponseDto.setTotalPrice(totalOrderPrice);
		orderResponseDto.setOrderItems(orderItemResponseDtoList);
		return orderResponseDto;
	}

	


}
