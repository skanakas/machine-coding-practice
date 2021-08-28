package com.splitwise.app;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.splitwise.models.PaySplit;

public class TestDifferentDebitAmounts {
	@Test
	public void testTwoLendersOneBorrower() throws Exception {
		Application app = new Application();
		app.getUserService().addUser("venkat");
		app.getUserService().addUser("bhav");
		app.getUserService().addUser("kiriti");
		app.getUserService().addUser("karthik");
		Set<PaySplit> borrowers = new HashSet<>();
		
		borrowers.add(new PaySplit(2,100));
		
		Set<PaySplit> splits= new HashSet<>();
		splits.add(new PaySplit(1, 60));
		splits.add(new PaySplit(3, 40));
		
		app.getExpenseService().addExpense(splits, borrowers, "Restaurant bill");
		app.getReportService().printReport(2);
		
	}
	
	@Test
	public void testLenderAndBorrowerSame() throws Exception {
		Application app = new Application();
		app.getUserService().addUser("venkat");
		app.getUserService().addUser("bhav");
		app.getUserService().addUser("kiriti");
		app.getUserService().addUser("karthik");
		Set<PaySplit> borrowers = new HashSet<>();
		
		borrowers.add(new PaySplit(1,100));

		Set<PaySplit> splits= new HashSet<>();
		splits.add(new PaySplit(1, 60));
		splits.add(new PaySplit(3, 40));
		
		app.getExpenseService().addExpense(splits, borrowers, "Restaurant bill");
		app.getReportService().printReport(1);
		
	}
}
