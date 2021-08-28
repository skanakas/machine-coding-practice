package com.mp.model.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Customer {
	
	private String accountId;
	
	ShoppingCart cart;

}
