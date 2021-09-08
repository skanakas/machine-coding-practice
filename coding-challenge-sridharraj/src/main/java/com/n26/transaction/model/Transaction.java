package com.n26.transaction.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.ToString;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction implements Serializable {

	private static final long serialVersionUID = 314307978570715146L;
	private String amount;
	@JsonIgnore
	private BigDecimal amountInDecimal;
	private Instant timestamp;

	public Transaction() {}

	public Transaction(String amount, Instant timeStamp) {
		this.amount = amount;
		this.timestamp = timeStamp;
	}

	public Transaction(BigDecimal amountInDecimal, Instant timeStamp) {
		this.amountInDecimal = amountInDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		this.timestamp = timeStamp;
	}

	public void setAmountDecimal(BigDecimal amountInDecimal) {
		this.amountInDecimal = amountInDecimal.setScale(2,BigDecimal.ROUND_HALF_UP);
	}

	@Override
	public int hashCode() {
		return Objects.hash(amountInDecimal, timestamp);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		return Objects.equals(amountInDecimal, other.amountInDecimal) && Objects.equals(timestamp, other.timestamp);
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public BigDecimal getAmountInDecimal() {
		return amountInDecimal;
	}

	public void setAmountInDecimal(BigDecimal amountInDecimal) {
		this.amountInDecimal = amountInDecimal;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}
}
