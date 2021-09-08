/**
 * 
 */
package com.n26.transaction.validation;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import com.n26.transaction.exception.InvalidTransactionException;
import com.n26.transaction.exception.InvalidTransactionException.ErrorType;
import com.n26.transaction.model.Transaction;

/**
 * @author SridharRaj
 *
 */
public class TransactionValidator {

	private final long timeDuration;
	private final ChronoUnit timeUnit;

	public TransactionValidator(long duration, ChronoUnit unit) {
		this.timeDuration = duration;
		this.timeUnit = unit;
	}

	public void validateTransactionForStatisticsComputation(final Transaction transaction) throws InvalidTransactionException {
		
		validateTransactionTimeFrame(transaction);
		
		validateTransactionAmount(transaction);
		
	}

	private void validateTransactionAmount(final Transaction transaction) throws InvalidTransactionException {
		
		try {
            transaction.setAmountDecimal(new BigDecimal(transaction.getAmount()));
        } catch (Exception e) {
            throw new InvalidTransactionException(ErrorType.INVALID_EVENT, "Invalid Transaction amount");
        }
		
	}

	private void validateTransactionTimeFrame(final Transaction transaction) throws InvalidTransactionException {
		Instant currentTime = Instant.now();
		if(transaction.getTimestamp().isBefore(currentTime.minus(timeDuration, timeUnit))) {
			throw new InvalidTransactionException(ErrorType.NOT_ACCEPTED, String.format("Transaction not occured in last %d%s", timeDuration, timeUnit.name()));
		}
		
		if(transaction.getTimestamp().isAfter(currentTime))
			throw new InvalidTransactionException(ErrorType.INVALID_EVENT, "Transaction occured in future");
	}

	
}
