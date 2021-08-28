package com.mp.model.account;

import com.mp.model.order.Order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member extends Customer {
	
	Account account;
	Order order;

}
