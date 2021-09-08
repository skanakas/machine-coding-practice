package com.n26.transaction.repository;

import java.time.Instant;
import java.util.List;

import com.n26.transaction.model.Transaction;

/**
 * @author SridharRaj
 *
 */
public interface TransactionRepository {
	
	/**
	 * Persists transaction to data store/container
	 * 
	 * @param transaction
	 */
	void saveTransaction(Transaction transaction);
	
	/**
	 * Purge all transaction events
	 */
	void deleteAll();

	/**
	 * Query transaction in time range
	 * 
	 * @param startWindow inclusive
	 * @param endWindow exclusive
	 * @return
	 */
	List<Transaction> getTransactionsBetween(Instant startWindow, Instant endWindow);
	
	/**
	 * Get transactions at given time instant
	 * 
	 * @param timeStamp
	 * @return
	 */
	List<Transaction> findByTimeStamp(Instant timeStamp); 
}
