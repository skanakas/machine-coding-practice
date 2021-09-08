package com.n26.transaction.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.n26.transaction.domain.TransactionRollUp;
import com.n26.transaction.exception.StatsComputationException;
import com.n26.transaction.model.Transaction;
import com.n26.transaction.model.TransactionStatistics;
import com.n26.transaction.repository.TransactionRepository;

@Service
public class TransactionStatisticsService {

	private static final Logger logger = LoggerFactory.getLogger(TransactionStatisticsService.class);
	
	@Resource
	private TransactionRepository transactionRepository;
	
	public TransactionStatistics getStatisticsBetween(Instant startTime, Instant endTime) throws StatsComputationException {
		
		try {
			/**
			 * Get Details from Storage container
			 */
			List<Transaction> transactionEvents = transactionRepository.getTransactionsBetween(startTime, endTime);

			TransactionStatistics transactionStats =  new TransactionStatistics();

			/**
			 * No Transactions found
			 */
			if(CollectionUtils.isEmpty(transactionEvents)) {
				return transactionStats;
			}

			/**
			 * Compute stats
			 */
			TransactionRollUp transactionRollUp = transactionEvents
					.stream()
					.map(Transaction::getAmountInDecimal)
					.collect(TransactionRollUp.collector());

			/**
			 * Build domain object
			 */
			transactionStats.setMin(transactionRollUp.getMin());
			transactionStats.setMax(transactionRollUp.getMax());
			transactionStats.setCount(transactionRollUp.getCount());
			transactionStats.setSum(transactionRollUp.getSum());
			transactionStats.setAvg(transactionRollUp.getSum().divide(new BigDecimal(transactionRollUp.getCount()), RoundingMode.HALF_UP));

			if(logger.isInfoEnabled())
				logger.info("Computed stats {}", transactionStats.toString());

			return transactionStats;
		} catch (Exception exception) {
			logger.error("Exception in computing stats", exception);
			throw new StatsComputationException("Exception in computing stats", exception);
		}
		
	}

}
