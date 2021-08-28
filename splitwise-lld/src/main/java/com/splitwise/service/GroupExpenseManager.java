package com.splitwise.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.splitwise.model.Expense;
import com.splitwise.model.ExpenseMetaData;
import com.splitwise.model.ExpenseType;
import com.splitwise.model.Split;
import com.splitwise.model.User;
import com.splitwise.model.exception.ValidationException;
import com.splitwise.model.exception.ValidationException.Type;

public class GroupExpenseManager {
	
	private TreeSet<Expense> expenseList = new TreeSet<>((e1,e2)-> e1.getCreatedTime().compareTo(e2.getCreatedTime()));
	
	private List<Expense> expenses = null;
	public Map<String, User> userMap = null;
	private Map<String, Map<String, Double>> balanceSheet = null;
	
	public GroupExpenseManager() {
		expenses = new CopyOnWriteArrayList<>();
		userMap = new ConcurrentHashMap<>();
		balanceSheet = new ConcurrentHashMap<String, Map<String, Double>>();
	}
	
	public void addUser(User user) throws ValidationException {
		if(userMap.get(user.getUserName())!= null) {
			throw new ValidationException(Type.DUPLICATE_USER, "User already part of the group");
		}
		
		userMap.put(user.getUserName(), user);
		balanceSheet.put(user.getUserName(), new ConcurrentHashMap<String, Double>());
	}
	
	public void addExpense(ExpenseType expenseType, String username, Double amount, List<Split> splits, ExpenseMetaData metaData) {
		User owner = this.userMap.get(username);
		Expense expense = ExpenseService.createExpense(expenseType, amount, owner, splits, metaData);
		expenses.add(expense);
		expenseList.add(expense);
		String ownerUserName = owner.getUserName();
		
		for(Split split : splits) {
			/**
			 * update recipient owes
			 */
			String paidTo = split.getUser().getUserName();
			
			Map<String, Double> ownerIncomeMap = balanceSheet.get(ownerUserName);
			if(ownerIncomeMap.get(paidTo)==null) {
				ownerIncomeMap.put(paidTo, 0.0);
			}
			ownerIncomeMap.put(paidTo, ownerIncomeMap.get(paidTo)+split.getAmount());
			
			/**
			 * update my owe
			 */
			Map<String, Double> toUserBalance = balanceSheet.get(paidTo);
			if(toUserBalance.get(ownerUserName) == null) {
				toUserBalance.put(ownerUserName, 0.0);
			}
			toUserBalance.put(ownerUserName, toUserBalance.get(ownerUserName) - split.getAmount());
		}
	}
	
	public void showBalances() throws ValidationException {
		for(Entry<String, Map<String, Double>> balances : balanceSheet.entrySet()) {
			
			for (Map.Entry<String, Double> userBalance : balances.getValue().entrySet()) {
                if (userBalance.getValue() > 0) {
                    printBalance(balances.getKey(), userBalance.getKey(), userBalance.getValue());
                }
            }
		}
	}
	
	public void showBalance(String username) throws ValidationException {
		if(userMap.get(username)== null) {
			throw new ValidationException(Type.INVALID_INPUT, "User not part of the group");
		}
		
		for(Entry<String, Double> balanceEntry : balanceSheet.get(username).entrySet()) {
			
			if(balanceEntry.getValue() != 0.0) {
				printBalance(username, balanceEntry.getKey(), balanceEntry.getValue());
			}
			
		}
	}

	private void printBalance(String user1, String user2, Double amount) {
		
		if(amount < 0) {
			System.out.println(user1 +"owes"+user2+" : "+ Math.abs(amount) );
		} else {
			System.out.println(user2 +"owes"+user1+" : "+ Math.abs(amount) );
		}
		
	}

}
