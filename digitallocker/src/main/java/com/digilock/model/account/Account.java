package com.digilock.model.account;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class Account {
	
	protected String userID;
	protected String userName;
	protected String password;
	protected PersonParty personParty;
	protected LocalDateTime timeCreated;

}
