package com.splitwise.model;

import lombok.Getter;
import lombok.Setter;

public abstract class Split {
	
	@Getter private User user;
	@Setter @Getter private Double amount;
	
	
	protected Split(User user) {
		this.user = user;
	}
	
}
