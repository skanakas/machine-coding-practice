package com.parkinglot.model.account;

import com.parkinglot.model.common.Address;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Contact {
	
	private String phone;
	private String email;
	private Address address;
	private Person personParty;

}
