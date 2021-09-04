package com.digilock.service;

import java.util.UUID;

public class AccountService {
	
	public String getUserIdForOrder(String orderId) {
		return UUID.randomUUID().toString();
	}
	
}
