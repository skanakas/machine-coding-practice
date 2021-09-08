/**
 * 
 */
package com.n26.transaction.service;

import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.n26.transaction.model.Transaction;
import com.n26.transaction.repository.TransactionRepository;

/**
 * @author SridharRaj
 *
 */
@Service
public class TransactionService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	TransactionRepository transactionRepository;

	public List<Transaction> getTransactionsAt(Instant instant) {
		return transactionRepository.findByTimeStamp(instant);
	}

	public void saveTransaction(Transaction transaction) {
		transactionRepository.saveTransaction(transaction);
	}

	public void clearAll() {
		transactionRepository.deleteAll();
	}

}
