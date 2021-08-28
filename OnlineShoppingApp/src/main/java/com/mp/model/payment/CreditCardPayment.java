package com.mp.model.payment;

import java.util.Currency;

import com.mp.account.fundingSource.CreditCard;

public class CreditCardPayment extends Payment {

	public CreditCardPayment(String paymentID, String activityID, Currency amount,
			CreditCard creditCard) {
		super(paymentID, activityID, amount, creditCard);
	}


}
