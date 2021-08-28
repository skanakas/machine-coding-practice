package com.splitwise.model;

import java.time.LocalDateTime;
import java.util.List;

import com.splitwise.model.exception.ValidationException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class Expense {
	
	private String expenseId;
	private User owner;
	private Double amount;
	private List<Split> splits;
	private ExpenseMetaData metaData;
	private LocalDateTime createdTime;
	
	public abstract boolean validate() throws ValidationException ;
	
	
	
}
