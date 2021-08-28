package com.splitwise.model;

import java.time.LocalDateTime;
import java.util.List;

public final class EqualExpense extends Expense{

	public EqualExpense(String expenseId, User owner, Double amount, List<Split> splits, ExpenseMetaData metaData) {
		super(expenseId, owner, amount, splits, metaData, LocalDateTime.now());
	}

	@Override
	public boolean validate() {
		
		for(Split split : super.getSplits()) {
			if(!(split instanceof EqualSplit))
				return false;
		}
		
		return true;
	}

	

}
