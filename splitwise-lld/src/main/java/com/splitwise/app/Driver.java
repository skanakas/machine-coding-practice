package com.splitwise.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.splitwise.model.EqualSplit;
import com.splitwise.model.ExactSplit;
import com.splitwise.model.ExpenseType;
import com.splitwise.model.PercentSplit;
import com.splitwise.model.Split;
import com.splitwise.model.User;
import com.splitwise.model.exception.ValidationException;
import com.splitwise.service.GroupExpenseManager;

/**
 * EXPENSE <user-id-of-person-who-paid> <amount-in-decimal> <no-of-users> <space-separated-list-of-users> <EQUAL/EXACT/PERCENT> <space-separated-values-in-case-of-non-equal>
 * 
 * @author arun
 *
 */
public class Driver {

	public static void main(String[] args) throws ValidationException {
		GroupExpenseManager expenseManager = new GroupExpenseManager();

		expenseManager.addUser(new User("u1", "gaurav@workat.tech", "9876543210"));
		expenseManager.addUser(new User("u2", "sagar@workat.tech", "9876543210"));
		expenseManager.addUser(new User("u3", "hi@workat.tech", "9876543210"));
		expenseManager.addUser(new User("u4", "mock-interviews@workat.tech", "9876543210"));

		Scanner scanner = new Scanner(System.in);
		while (true) {
			String command = scanner.nextLine();
			String[] commands = command.split(" ");
			String commandType = commands[0];

			switch (commandType) {
			case "SHOW":
				if (commands.length == 1) {
					expenseManager.showBalances();
				} else {
					expenseManager.showBalance(commands[1]);
				}
				break;
			case "EXPENSE":
				String paidBy = commands[1];
				Double amount = Double.parseDouble(commands[2]);
				int noOfUsers = Integer.parseInt(commands[3]);
				String expenseType = commands[4 + noOfUsers];
				List<Split> splits = new ArrayList<>();
				switch (expenseType) {
				case "EQUAL":
					for (int i = 0; i < noOfUsers; i++) {
						splits.add(new EqualSplit(expenseManager.userMap.get(commands[4 + i])));
					}
					expenseManager.addExpense(ExpenseType.EQUAL, paidBy, amount, splits, null);
					break;
				case "EXACT":
					for (int i = 0; i < noOfUsers; i++) {
						splits.add(new ExactSplit(expenseManager.userMap.get(commands[4 + i]), Double.parseDouble(commands[5 + noOfUsers + i])));
					}
					expenseManager.addExpense(ExpenseType.EXACT, paidBy, amount, splits, null);
					break;
				case "PERCENT":
					for (int i = 0; i < noOfUsers; i++) {
						splits.add(new PercentSplit(expenseManager.userMap.get(commands[4 + i]), Double.parseDouble(commands[5 + noOfUsers + i])));
					}
					expenseManager.addExpense(ExpenseType.PERCENT, paidBy, amount, splits, null);
					break;
				}
				break;
			}
		}
	}

}
