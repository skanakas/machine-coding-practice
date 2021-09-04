package com.digilock.model.account;

import java.time.LocalDateTime;

public class DeliveryAgent extends Account {

	public DeliveryAgent(String userID, String userName, String password, PersonParty personParty,
			LocalDateTime timeCreated) {
		super(userID, userName, password, personParty, timeCreated);
	}

}
