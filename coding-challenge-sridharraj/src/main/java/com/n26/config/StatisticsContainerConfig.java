/**
 * 
 */
package com.n26.config;

import java.time.temporal.ChronoUnit;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.n26.transaction.repository.InMemoryTransactionRepository;
import com.n26.transaction.repository.TransactionRepository;
import com.n26.transaction.validation.TransactionValidator;

/**
 * @author SridharRaj
 *
 */
@Configuration
public class StatisticsContainerConfig {
	
	/**
	 * Configure the sliding window log period
	 * TODO : can be injected from a remote config property
	 * 
	 * @return
	 */
	@Bean
	public Pair<Integer, ChronoUnit> defaultRollingPeriod(){
		return new ImmutablePair<>(60, ChronoUnit.SECONDS);
	}
	
	@Autowired
	private Pair<Integer, ChronoUnit> defaultRollingPeriod;

	/**
	 * TODO : N number of sliding windows with different time window 
	 * 		  can also be created in future and autowire the below beans in service as LIST
	 * 
	 * @return
	 */
	@Bean(name = "slidingWindowLogRepo")
	public TransactionRepository rollingWindowWith60Sec() {
		return new InMemoryTransactionRepository(defaultRollingPeriod.getKey(), defaultRollingPeriod.getValue()); 
	}
	
	@Bean(name = "statisticsPreValidator")
	public TransactionValidator statisticsPreValidator() {
		return new TransactionValidator(defaultRollingPeriod.getKey(), defaultRollingPeriod.getValue());
	}
	
}
