package com.splitwise.model;

import lombok.Getter;

public class PercentSplit extends Split {
	
	@Getter private Double percent;

	public PercentSplit(User user, Double percent) {
		super(user);
		this.percent = percent;
	}

}
