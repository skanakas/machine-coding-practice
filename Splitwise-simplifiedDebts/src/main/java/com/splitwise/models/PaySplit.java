package com.splitwise.models;

public class PaySplit {
	
	private int userId;
	private double amount;

	public PaySplit(int userId, double amount) {
		this.userId = userId;
		this.amount = amount;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "PaySplit [userId=" + userId + ", amount=" + amount + "]";
	}

}
