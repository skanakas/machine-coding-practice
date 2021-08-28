package com.splitwise.app;

import com.splitwise.models.ConsolidatedExpenseData;
import com.splitwise.service.ExpenseService;
import com.splitwise.service.GroupService;
import com.splitwise.service.ReportService;
import com.splitwise.service.SimplificationService;
import com.splitwise.service.UserService;

public class Application {
	private UserService userService;
	private GroupService groupService;
	private ExpenseService expenseService;
	private ReportService reportService;
	private ConsolidatedExpenseData data;
	private SimplificationService simplificationService;

	public Application() {
		userService = new UserService();
		groupService = new GroupService();

		data = new ConsolidatedExpenseData();
		reportService = new ReportService(data, userService);
		expenseService = new ExpenseService(userService, groupService, data);
		simplificationService = new SimplificationService(data, userService);
	}

	public UserService getUserService() {
		return userService;
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public ExpenseService getExpenseService() {
		return expenseService;
	}

	public ReportService getReportService() {
		return reportService;
	}

	public SimplificationService getSimplificationService() {
		return simplificationService;
	}
}
