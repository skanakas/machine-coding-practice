package com.klarna.practice;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TransactionsSampleTest {

	@Test
	public void shouldReturnEmptyListIfThereIsNoTransactions() {
		assertThat(Transactions.findRejectedTransactions(new ArrayList<>(), 0).size(), is(0));
	}

	@Test
	public void shouldReturnEmptyListIfThereIsATransactionWithinCreditLimit() {
		List<String> transactions = Arrays.asList("John,Doe,john@doe.com,200,TR0001");

		List<String> rejectedTransactions = Transactions.findRejectedTransactions(transactions, 200);

		assertThat(rejectedTransactions.size(), is(0));
	}

	@Test
	public void shouldReturnTransationThatIsOverCreditLimit() {
		List<String> transactions = Arrays.asList("John,Doe,john@doe.com,201,TR0001");

		List<String> rejectedTransactions = Transactions.findRejectedTransactions(transactions, 200);

		assertThat(rejectedTransactions, is(Arrays.asList("TR0001")));
	}
	/**
	 * List<String> data = new ArrayList<>();
		data.add("John1,Doe,john@doe.com,200,TR001");
		data.add("John1,Doe,john@doe.com,300,TR002");
		data.add("John1,Doe,john@doe.com,400,TR003");
		data.add("John1,Doe,john@doe.com,500,TR004");
		
		data.add("John,Doe,john2@doe.com,100,TR005");
		data.add("John,Doe,john2@doe.com,100,TR006");
		data.add("John,Doe,john2@doe.com,200,TR007");
		data.add("John,Doe,john2@doe.com,300,TR008");
	 */
	@Test
	public void shouldReturnTransationThatIsOverCreditLimitWithMultipleUsers() {
		List<String> transactions = Arrays.asList(
				"John1,Doe,john@doe.com,200,TR001",
				"John1,Doe,john@doe.com,300,TR002",
				"John1,Doe,john@doe.com,400,TR003",
				"John1,Doe,john@doe.com,500,TR004",
				"John,Doe,john2@doe.com,100,TR005",
				"John,Doe,john2@doe.com,100,TR006",
				"John,Doe,john2@doe.com,200,TR007",
				"John,Doe,john2@doe.com,300,TR008"
				);

		List<String> rejectedTransactions = Transactions.findRejectedTransactions(transactions, 200);
		
		assertNotNull(rejectedTransactions);
		assertTrue(Arrays.asList("TR002, TR003, TR004, TR007, TR008").toString().equals(rejectedTransactions.toString()));
		
	}
	
	@Test
	public void multiPleUsersAndOneWithLimitAndOtherCrossed() {
		List<String> transactions = Arrays.asList(
				"John1,Doe,john@doe.com,100,TR001",
				"John1,Doe,john@doe.com,100,TR002",
				"John1,Doe,john@doe.com,100,TR003",
				"John1,Doe,john@doe.com,100,TR004",
				"John,Doe,john2@doe.com,100,TR005",
				"John,Doe,john2@doe.com,100,TR006",
				"John,Doe,john2@doe.com,200,TR007",
				"John,Doe,john2@doe.com,300,TR008"
				);

		List<String> rejectedTransactions = Transactions.findRejectedTransactions(transactions, 500);
		
		assertNotNull(rejectedTransactions);
		assertTrue(Arrays.asList("TR008").toString().equals(rejectedTransactions.toString()));
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidTransactionAmount() {
		List<String> transactions = Arrays.asList(
				"John1,Doe,john@doe.com,XXX,TR001",
				"John1,Doe,john@doe.com,400,TR003",
				"John1,Doe,john@doe.com,500,TR004",
				"John,Doe,john2@doe.com,100,TR005",
				"John,Doe,john2@doe.com,XXX,TR007",
				"John,Doe,john2@doe.com,300,TR008"
				);

		Transactions.findRejectedTransactions(transactions, 200);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void userTransactionWithoutEMail() {
		List<String> transactions = Arrays.asList(
				"John1,Doe,100,TR001",
				"John1,Doe,john@doe.com,400,TR003",
				"John1,Doe,john@doe.com,500,TR004",
				"John,Doe,john2@doe.com,100,TR005",
				"John,Doe,john2@doe.com,XXX,TR007",
				"John,Doe,john2@doe.com,300,TR008"
				);

		Transactions.findRejectedTransactions(transactions, 200);
	}


}