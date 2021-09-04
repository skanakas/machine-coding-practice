package com.digilock.service;

import java.util.List;

import com.digilock.model.Order;
import com.digilock.model.OrderItem;
import com.digilock.repo.OrderRepo;

public class OrderService {

	private OrderRepo orderRepo = new OrderRepo();

	public Order fetchOrderDetails(String orderID) {
		return orderRepo.getOrderByOrderID(orderID);
	}
	public List<OrderItem> getItemsForOrder(String orderId) {
		return orderRepo.getOrderByOrderID(orderId).getOrderItems();
	}

	public void initiateRefund(String orderId) {
		System.out.printf("Refund for order %s initiated", orderId);
	}


}
