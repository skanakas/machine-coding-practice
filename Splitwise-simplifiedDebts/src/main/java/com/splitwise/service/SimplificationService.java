package com.splitwise.service;

import java.util.Map;
import java.util.Set;

import com.splitwise.models.BFSResult;
import com.splitwise.models.ConsolidatedExpenseData;

public class SimplificationService {

	Map<Integer, Map<Integer, Double>> creditMap;
	Map<Integer, Map<Integer, Double>> debitMap;
	private ConsolidatedExpenseData data;
	private UserService userService;
	public SimplificationService(ConsolidatedExpenseData data, UserService userService) {
		this.creditMap = data.getCreditMap();
		this.debitMap = data.getDebitMap();
		this.data = data;
		this.userService = userService;
	}
	
	public void simplifyExpenses() {
		simplifyCommutative();
		simplifyTransitive();
	}

	private void simplifyTransitive() {
		BFSHelper bfsUtil = new BFSHelper(data, userService);
		for(Integer eachUser : debitMap.keySet()) {
			BFSResult result;
			while((result = bfsUtil.findPath(eachUser)) != null) {
				for(int i = 0; i < result.getPath().size(); i++) {
					int currNode = result.getPath().get(i);
					int nextNode = result.getPath().get((i+1) % result.getPath().size());
					double updatedCost = debitMap.get(currNode).get(nextNode) - result.getMinCost();
					updateCost(debitMap.get(currNode), nextNode, updatedCost);
					updateCost(creditMap.get(nextNode), currNode, updatedCost);
				}
			}
		}
		
		
	}

	private void simplifyCommutative() {
		Set<Integer> allUsers = userService.getAllUserIds();
		for(Integer eachUser : allUsers) {
			if(debitMap.containsKey(eachUser)) {
				Map<Integer, Double> lenderMap = debitMap.get(eachUser);
				for(Integer eachLender: lenderMap.keySet()) {
					if(debitMap.get(eachLender).containsKey(eachUser)) {
						double amount1 = debitMap.get(eachLender).get(eachUser);
						double amount2 = debitMap.get(eachUser).get(eachLender);
						double amountToClear = Math.min(amount1, amount2);
						//update debits
						updateCost(debitMap.get(eachLender), eachUser, amount1 - amountToClear);
						updateCost(debitMap.get(eachUser), eachLender, amount2 - amountToClear);
						//update credits
						updateCost(creditMap.get(eachUser), eachLender, amount1 - amountToClear);
						updateCost(creditMap.get(eachLender), eachUser, amount2 - amountToClear);
					}
				}
			}
		}
	}
	
	private void updateCost(Map<Integer, Double> map,int user, double updatedCost) {
		if(updatedCost == 0.0) {
			map.remove(user);
		}else {
			map.put(user, updatedCost);
		}
	}
}
