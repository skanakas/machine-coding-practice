package com.digilock.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.digilock.model.account.Account;
import com.digilock.model.account.Buyer;
import com.digilock.model.account.DeliveryAgent;

public class InMemoryAccountRepo {

	public static final List<Account> users = new ArrayList<>();
	public static final Map<String, Buyer> userMap = new HashMap<>();
	public static final Map<String, DeliveryAgent> deliveryAgents = new HashMap<>();
	

	public Account getAccount(String uderID) {
		return userMap.get(uderID);
	}

	public Account createBuyer(Buyer user) {
		users.add(user);
		userMap.putIfAbsent(user.getUserID(), user);
		return user;
	}

	public Account createDeliveryAgent(DeliveryAgent user) {
		users.add(user);
		deliveryAgents.putIfAbsent(user.getUserID(), user);
		return user;
	}


}
