package com.splitwise.service;

import java.util.Map;

import com.splitwise.models.ConsolidatedExpenseData;

public class ReportService {
	
	private ConsolidatedExpenseData data; 
	private UserService userService;
	
	public ReportService(ConsolidatedExpenseData data, UserService userService) {
		this.userService = userService;
		this.data = data;
	}
	
	public void printReport(int userId) throws Exception {
		Map<Integer, Map<Integer,Double>> creditMap = data.getCreditMap();
		Map<Integer, Map<Integer,Double>> debitMap = data.getDebitMap();
		System.out.println("****** Credits ******");
		if (creditMap.containsKey(userId)) {
			Map<Integer, Double> debitors = creditMap.get(userId);
			String lenderName = userService.findUser(userId).getUserName();
			for (Integer eachBorrower : debitors.keySet()) {
				String borrowerName = userService.findUser(eachBorrower).getUserName();
				System.out.println(borrowerName + " owes " + lenderName + " Rs." + debitors.get(eachBorrower));
			}
		}
		System.out.println("****** Debits ******");
		if (debitMap.containsKey(userId)) {
			Map<Integer, Double> debitors = debitMap.get(userId);
			String borrowerName = userService.findUser(userId).getUserName();
			for (Integer eachLender : debitors.keySet()) {
				String lenderName = userService.findUser(eachLender).getUserName();
				System.out.println(borrowerName + " owes " + lenderName + " Rs." + debitors.get(eachLender));
			}
		}
	}

	public void printOverallReport() throws Exception {
		for (Integer eachUser : userService.getAllUserIds()) {
			printReport(eachUser);
			System.out.println("#######################################################");
		}
	}
}
