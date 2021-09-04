package com.digilock.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserLockerNotification {

	private String userID;
	private String orderID;
	private String lockerID;
	private String otp;
	
}
