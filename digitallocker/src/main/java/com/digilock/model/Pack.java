package com.digilock.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pack {
	
	private String id;
	private String orderID;
	private List<OrderItem> orderItems;
	private LocalDateTime createdTime;
	private PackageSize size;
	private Delivery deliveryDetails;
	private Random random = new Random();
	
	
	public Pack(String orderId, List<OrderItem> orderItems, Delivery delivery) {
		this.id = UUID.randomUUID().toString();
		this.orderID = orderId;
		this.orderItems = orderItems;
		this.createdTime = LocalDateTime.now();
		this.deliveryDetails = delivery;
		pack();
	}

	private void pack() {
		size = PackageSize.fromId(random.nextInt(PackageSize.values().length));
	}

}
