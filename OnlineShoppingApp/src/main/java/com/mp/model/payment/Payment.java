package com.mp.model.payment;

import java.util.Currency;

import com.mp.account.fundingSource.FundingInstrument;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Payment {
	
	private String paymentID;
	private String activityID;
	private Currency amount;
	private FundingInstrument fundingInstrument;

}
