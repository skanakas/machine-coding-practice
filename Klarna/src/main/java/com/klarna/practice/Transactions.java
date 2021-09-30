package com.klarna.practice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

class Transaction{
	String firstName;
	String lastName;
	String email;
	Integer amount;
	String txnID;

	private Transaction(String firstName, String lastName, String email, Integer amount, String txnID) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.amount = amount;
		this.txnID = txnID;
	}
	
	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("Transaction [firstName=").append(firstName).append(", lastName=").append(lastName)
				.append(", email=").append(email).append(", amount=").append(amount).append(", txnID=").append(txnID)
				.append("]");
		return builder2.toString();
	}

	public static Builder newBuilder(String input) {
		return new Builder(input);
	}

	public String getKey() {
		StringBuilder key = new StringBuilder();
		key.append(this.firstName).append("|");
		key.append(this.lastName).append("|");
		key.append(this.email);
		return key.toString();
	}

	public static class Builder {

		private final String input;
		public Builder(String input) {
			this.input = input;
		}

		public Transaction build() {
			if(input==null || input.isEmpty()) {
				throw new IllegalArgumentException("invalidInput");
			}

			String[] data = input.split(",");
			if(data.length!=5) {
				throw new IllegalArgumentException("invalidInput");
			}

			String firstName = data[0];
			String lastName = data[1];
			String email = data[2];
			String amount = data[3];
			Integer amountNum = 0;
			try {
				amountNum = Integer.parseInt(amount);
			} catch(NumberFormatException e) {
				throw new IllegalArgumentException(e);
			}
			String txnID = data[4];

			return new Transaction(firstName, lastName, email, amountNum, txnID);
		}
	}

}



public class Transactions {
	public static List<String> findRejectedTransactions(List<String> transactions, int creditLimit) {
		
		if(transactions==null || transactions.isEmpty()) {
			return Collections.emptyList();
		}
		
		List<Transaction> allTransactions = new ArrayList<>();
		for(String txn : transactions) {
			Transaction transaction = Transaction.newBuilder(txn).build();
			allTransactions.add(transaction);
		}
		
		Map<String, List<Transaction>> userToTransactionMap = allTransactions.stream()
				.collect(Collectors.groupingBy(Transaction::getKey));
		
		System.out.println(userToTransactionMap);
		
		Map<String, Integer> userToCurrCredit = new HashMap<>();
		
		List<String> rejectedTransactions = new ArrayList<>();
		for(Entry<String, List<Transaction>> userEntry : userToTransactionMap.entrySet()) {
			String user = userEntry.getKey();
			List<Transaction> userTxns = userEntry.getValue();
			Integer currSum = userToCurrCredit.getOrDefault(user, 0);
			for(Transaction txn : userTxns) {
				currSum+= txn.amount;
				if(currSum > creditLimit) {
					rejectedTransactions.add(txn.txnID);
					currSum-=txn.amount;
				}
				userToCurrCredit.put(user, currSum);
			}
		}
		
		System.out.println(rejectedTransactions);
		
		return rejectedTransactions;
	}
	
	public static void main(String[] args) {
		//Transaction transaction = Transaction.newBuilder("John,Doe,john@doe.com,30,TR000").build();
		//System.out.println(transaction);
		
		List<String> data = new ArrayList<>();
		data.add("John1,Doe,john@doe.com,200,TR001");
		data.add("John1,Doe,john@doe.com,200,TR002");
		data.add("John1,Doe,john@doe.com,400,TR003");
		data.add("John1,Doe,john@doe.com,500,TR004");
		
		data.add("John,Doe,john2@doe.com,100,TR005");
		data.add("John,Doe,john2@doe.com,100,TR006");
		data.add("John,Doe,john2@doe.com,200,TR007");
		data.add("John,Doe,john2@doe.com,300,TR008");
		
		data.add("John,Doe,john4@doe.com,600,TR009");
		
		data.add("John1,Doe,john@doe.com,100,TR0010");
		
		findRejectedTransactions(data, 500);
	}
}
