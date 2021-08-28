package com.splitwise.models;

import java.util.HashMap;
import java.util.Map;

public class ConsolidatedExpenseData {
	Map<Integer, Map<Integer, Double>> creditMap;
	Map<Integer, Map<Integer, Double>> debitMap;

	public ConsolidatedExpenseData() {
		this.creditMap = new HashMap<>();
		this.debitMap = new HashMap<>();
	}

	public Map<Integer, Map<Integer, Double>> getCreditMap() {
		return creditMap;
	}

	public Map<Integer, Map<Integer, Double>> getDebitMap() {
		return debitMap;
	}
}
