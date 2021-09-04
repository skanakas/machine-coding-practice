package com.digilock.model.account;

import java.time.LocalDateTime;

import com.digilock.model.common.GeoLocation;

import lombok.Getter;

@Getter
public class Buyer extends Account {
	
	private GeoLocation geoLocation;
	
	public Buyer(String userId, String userName,
			String password,
			PersonParty personParty, LocalDateTime timeCreated,
			GeoLocation userLocation) {
		super(userId, userName, password, personParty, timeCreated);
		this.geoLocation = userLocation;
	}
}
