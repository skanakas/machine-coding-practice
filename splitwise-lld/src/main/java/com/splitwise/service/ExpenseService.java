package com.splitwise.service;

import java.util.List;
import java.util.UUID;

import com.splitwise.model.EqualExpense;
import com.splitwise.model.ExactExpense;
import com.splitwise.model.Expense;
import com.splitwise.model.ExpenseMetaData;
import com.splitwise.model.ExpenseType;
import com.splitwise.model.PercentSplit;
import com.splitwise.model.PercentageSplitExpense;
import com.splitwise.model.Split;
import com.splitwise.model.User;

public class ExpenseService {
	/**
	 * 
	 * 
	 * @return Expense obj
	 */
	public static Expense createExpense(ExpenseType expenseType, Double amount, User paidBy, List<Split> splits, ExpenseMetaData metaData) {
		
		Expense expense = null;
		String expenseID = UUID.randomUUID().toString();
		
		switch (expenseType) {
		case EQUAL:
			EqualExpense equalExpense = new EqualExpense(expenseID, paidBy, amount, splits, metaData);
			
			int totalSplits = splits.size();
			Double eachSplitAmount = ((double)Math.abs(amount*100/totalSplits))/100.0;
			for(Split split : splits) {
				split.setAmount(eachSplitAmount);
			}
			// Balance - re-balance
			splits.get(0).setAmount(splits.get(0).getAmount() + (amount - (eachSplitAmount*totalSplits)));
			expense = equalExpense;
			
			break;
		case EXACT:
			expense = (Expense) new ExactExpense(expenseID, paidBy, amount, splits, metaData);
			break;
		case PERCENT:
			
			Double totalSplitSum = 0.0;
			for(Split split : splits) {
				PercentSplit percentSplit = (PercentSplit)split;
				double percentSplitamount = (amount * percentSplit.getPercent())/100.0;
				split.setAmount(percentSplitamount);
				totalSplitSum += percentSplitamount;
			}
			//Re-balance
			splits.get(0).setAmount(splits.get(0).getAmount() + (amount - totalSplitSum));
			
			PercentageSplitExpense splitExpense = new PercentageSplitExpense(expenseID, paidBy, amount, splits, metaData);
			expense = splitExpense;
			
			break;
		default:
			break;
		}
		
		return expense;
	}

}
