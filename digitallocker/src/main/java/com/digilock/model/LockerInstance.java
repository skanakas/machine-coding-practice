package com.digilock.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LockerInstance {
	
	String lockerID;
	String orderID;
	String otp;
	
	public boolean verifyOPT(String otp) {
		return this.otp.equals(otp);
	}

}
