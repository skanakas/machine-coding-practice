package com.splitwise.model;

import lombok.Builder;
import lombok.Getter;

@Builder
public class ExpenseMetaData {
	
	@Getter private String name;
	@Getter private String details;
	@Getter private String billImageUrl;

}
