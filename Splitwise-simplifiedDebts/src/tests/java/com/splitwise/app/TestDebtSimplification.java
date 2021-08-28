package com.splitwise.app;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.splitwise.models.PaySplit;

public class TestDebtSimplification {
	@Test
	public void testTransitiveDebtSimplification() throws Exception {
		Application app = new Application();
		app.getUserService().addUser("A");
		app.getUserService().addUser("B");
		app.getUserService().addUser("C");
		app.getUserService().addUser("D");
		Set<PaySplit> borrowers = new HashSet<>();
		borrowers.add(new PaySplit(1, 100));

		Set<PaySplit> splits = new HashSet<>();
		splits.add(new PaySplit(2, 100));

		app.getExpenseService().addExpense(splits, borrowers, "Restaurant bill");
		
		Set<PaySplit> borrowers1 = new HashSet<>();
		borrowers1.add(new PaySplit(2, 100));

		Set<PaySplit> splits1 = new HashSet<>();
		splits1.add(new PaySplit(3, 100));

		app.getExpenseService().addExpense(splits1, borrowers1, "Restaurant bill");
		
		Set<PaySplit> borrowers2 = new HashSet<>();
		borrowers2.add(new PaySplit(3, 100));

		Set<PaySplit> splits2 = new HashSet<>();
		splits2.add(new PaySplit(4, 100));

		app.getExpenseService().addExpense(splits2, borrowers2, "Restaurant bill");
		
		Set<PaySplit> borrowers3 = new HashSet<>();
		borrowers3.add(new PaySplit(4, 100));

		Set<PaySplit> splits3 = new HashSet<>();
		splits3.add(new PaySplit(1, 100));

		app.getExpenseService().addExpense(splits3, borrowers3, "Restaurant bill");
		System.out.println("Before Simplification");
		app.getReportService().printOverallReport();
		app.getSimplificationService().simplifyExpenses();
		System.out.println("After Simplification");
		app.getReportService().printOverallReport();

	}
	
	
	@Test
	public void testMultipleCycles() throws Exception {
		Application app = new Application();
		app.getUserService().addUser("A");
		app.getUserService().addUser("B");
		app.getUserService().addUser("C");
		app.getUserService().addUser("D");
		app.getUserService().addUser("E");
		Set<PaySplit> borrowers = new HashSet<>();
		borrowers.add(new PaySplit(1, 100));

		Set<PaySplit> splits = new HashSet<>();
		splits.add(new PaySplit(2, 100));

		app.getExpenseService().addExpense(splits, borrowers, "Restaurant bill");
		
		Set<PaySplit> borrowers1 = new HashSet<>();
		borrowers1.add(new PaySplit(2, 100));

		Set<PaySplit> splits1 = new HashSet<>();
		splits1.add(new PaySplit(3, 100));

		app.getExpenseService().addExpense(splits1, borrowers1, "Restaurant bill");
		
		Set<PaySplit> borrowers2 = new HashSet<>();
		borrowers2.add(new PaySplit(3, 100));

		Set<PaySplit> splits2 = new HashSet<>();
		splits2.add(new PaySplit(1, 100));

		app.getExpenseService().addExpense(splits2, borrowers2, "Restaurant bill");
		
		Set<PaySplit> borrowers3 = new HashSet<>();
		borrowers3.add(new PaySplit(2, 100));

		Set<PaySplit> splits3 = new HashSet<>();
		splits3.add(new PaySplit(4, 100));

		app.getExpenseService().addExpense(splits3, borrowers3, "Restaurant bill");
		
		Set<PaySplit> borrowers4 = new HashSet<>();
		borrowers4.add(new PaySplit(4, 100));

		Set<PaySplit> splits4 = new HashSet<>();
		splits4.add(new PaySplit(5, 100));

		app.getExpenseService().addExpense(splits4, borrowers4, "Restaurant bill");
		
		Set<PaySplit> borrowers5 = new HashSet<>();
		borrowers5.add(new PaySplit(5, 100));

		Set<PaySplit> splits5 = new HashSet<>();
		splits5.add(new PaySplit(2, 100));

		app.getExpenseService().addExpense(splits5, borrowers5, "Restaurant bill");
		
		
		System.out.println("Before Simplification");
		app.getReportService().printOverallReport();
		app.getSimplificationService().simplifyExpenses();
		System.out.println("After Simplification");
		app.getReportService().printOverallReport();

	}
}
