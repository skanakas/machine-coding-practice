package com.digilock.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Order {
	
	private String orderID;
	private List<OrderItem> orderItems;
	private LocalDateTime orderCreatedTime;
	private Delivery deliveryDetails;

}
