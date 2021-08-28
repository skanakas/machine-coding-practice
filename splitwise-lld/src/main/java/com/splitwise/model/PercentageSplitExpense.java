package com.splitwise.model;

import java.time.LocalDateTime;
import java.util.List;

import com.splitwise.model.exception.ValidationException;

public class PercentageSplitExpense extends Expense {

	public PercentageSplitExpense(String expenseId, User owner, Double amount, List<Split> splits,
			ExpenseMetaData metaData) {
		super(expenseId, owner, amount, splits, metaData, LocalDateTime.now());
	}

	@Override
	public boolean validate() throws ValidationException {
		for (Split split : getSplits()) {
            if (!(split instanceof PercentSplit)) {
                return false;
            }
        }
        double totalPercent = 100;
        double sumSplitPercent = 0;
        for (Split split : getSplits()) {
            PercentSplit exactSplit = (PercentSplit) split;
            sumSplitPercent += exactSplit.getPercent();
        }
        if (totalPercent != sumSplitPercent) {
            return false;
        }
        return true;
	}

}
