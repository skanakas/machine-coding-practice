package com.mp.model.payment;

import java.util.Currency;

import com.mp.account.fundingSource.NetBanking;

public class BankingPayment extends Payment {

	public BankingPayment(String paymentID, String activityID, Currency amount, NetBanking netBanking) {
		super(paymentID, activityID, amount, netBanking);
	}

}
