package com.mp.model.order;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.mp.model.constants.OrderStatus;
import com.mp.model.product.ProductItem;
import com.mp.model.shipment.Shipment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Order {
	
	private String orderId;
	private OrderStatus orderStatus;
	//Many to one
	private List<Shipment> shipmentDetails;
	private Map<ProductItem, Shipment> shipmentMapping;
	private List<ProductItem> orderItems;
	private LinkedList<OrderLog> orderLog;
	
}
