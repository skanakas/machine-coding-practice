package com.splitwise.model;

import java.time.LocalDateTime;
import java.util.List;

import com.splitwise.model.exception.ValidationException;
import com.splitwise.model.exception.ValidationException.Type;

public class ExactExpense extends Expense {

	public ExactExpense(String expenseId, User owner, Double amount, List<Split> splits, ExpenseMetaData metaData) {
		super(expenseId, owner, amount, splits, metaData, LocalDateTime.now());
	}

	@Override
	public boolean validate() throws ValidationException {
		
		for(Split split : super.getSplits()) {
			if(!(split instanceof ExactSplit))
				return false;
		}
		
		Double totalAmount = this.getAmount();
		Double splitAmountSum = 0.0;
		for(Split s : super.getSplits()) {
			splitAmountSum += s.getAmount();
		}
		
		if(splitAmountSum != totalAmount) {
			throw new ValidationException(Type.INVALID_EQUAL_SPLIT, "INVALID_EQUAL_SPLIT");
		}
		
		return true;
	}

}
