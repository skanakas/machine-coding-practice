package com.mp.context;

import com.mp.model.order.Order;
import com.mp.model.payment.Payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderContext {
	
	SessionContext sessionContext;
	Order order;
	Payment payment;
}
