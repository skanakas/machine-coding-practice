package com.mp.context;

import com.mp.model.account.ShoppingCart;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShoppingContext {
	
	SessionContext sessionContext;
	ShoppingCart shoppingCart;

}
