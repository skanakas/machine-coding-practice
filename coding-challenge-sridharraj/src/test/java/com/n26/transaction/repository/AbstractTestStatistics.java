package com.n26.transaction.repository;

import java.time.temporal.ChronoUnit;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.After;
import org.junit.Before;

import com.n26.config.StatisticsContainerConfig;

public class AbstractTestStatistics {
	
	private StatisticsContainerConfig config = new StatisticsContainerConfig();
	private Pair<Integer, ChronoUnit> winidowSize = config.defaultRollingPeriod();
	protected InMemoryTransactionRepository transactionRepository;
	
	@Before
	public void init() {
		
		transactionRepository = new InMemoryTransactionRepository(winidowSize.getKey(), winidowSize.getValue());
		transactionRepository.postConstruct();
	}
	
	@After
	public void clean() {
		transactionRepository.cleanup();
	}
	
	protected Long getCleanUpCycleDuration() {
		
		Long val = winidowSize.getKey().intValue() * winidowSize.getValue().getDuration().toMillis();
		return val;
		
	}
	
}
