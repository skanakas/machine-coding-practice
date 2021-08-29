package com.booking.movie.model;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
public class TransactionDetails {
	
	private String txnID;
	private String paymentMode;
	/**
	 * TODO : Other payment details
	 */
	@Setter private PaymentStatus status;
	public enum PaymentStatus {
		INIT,
		SUCCESS,
		DECLINED,
		CANCELLED,
		REFUNDED
	}
	public TransactionDetails(String txnID, String paymentMode, PaymentStatus status) {
		super();
		this.txnID = txnID;
		this.paymentMode = paymentMode;
		this.status = PaymentStatus.INIT;
		this.txnID = UUID.randomUUID().toString();
	}

}
