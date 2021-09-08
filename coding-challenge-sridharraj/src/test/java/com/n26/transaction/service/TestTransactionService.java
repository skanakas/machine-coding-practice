package com.n26.transaction.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.n26.transaction.model.Transaction;
import com.n26.transaction.repository.AbstractTestStatistics;

@SpringBootTest
public class TestTransactionService extends AbstractTestStatistics{

	private TransactionService transactionService = null;
	@Before
	public void init() {
		super.init();
		this.transactionService = new TransactionService();
		this.transactionService.transactionRepository = super.transactionRepository;
	}

	@After
	public void cleanUp() {
		super.clean();
	}

	@Test
	public void testParallelPersistence() throws InterruptedException {

		int requestCount = 50, transactionsPerReq = 10;
		Set<Instant> primaryKeys = new HashSet<>();
		try {
			primaryKeys = sendParallelRequests(requestCount, transactionsPerReq);
		} catch (InterruptedException e) {
			Assert.fail("Exception when testing persistance layer");
		}

		for(Instant key : primaryKeys) {
			Assert.assertNotNull(this.transactionService.getTransactionsAt(key));
		}

		Long sleepDuration = getCleanUpCycleDuration();
		Thread.sleep(sleepDuration);

		for(Instant key : primaryKeys) {
			Assert.assertNull(this.transactionService.getTransactionsAt(key));
		}
	}


	private Set<Instant> sendParallelRequests(int requestCount, int transactionsPerReq) throws InterruptedException {
		Set<Instant> timeStamps = ConcurrentHashMap.newKeySet();
		Random r = new Random();
		int rangeMin = 0;
		int rangeMax = 9999;

		CountDownLatch doneSignal = new CountDownLatch(requestCount);
		for (int i = 0; i < requestCount; i++) {
			new Thread(() -> {
				try {
					Integer maxReqPerThread = transactionsPerReq;
					Instant now = Instant.now();

					while(maxReqPerThread > 0) {
						/**
						 * Random sleep to test 
						 */
						Thread.sleep(r.nextInt(100));
						double randomAmount = rangeMin  + (rangeMax  - rangeMin) * r.nextDouble();
						Transaction transaction = new Transaction(new BigDecimal(randomAmount).toString(), now);
						timeStamps.add(now);
						this.transactionService.saveTransaction(transaction);
						maxReqPerThread--;
					}
				} catch (InterruptedException e) {
					Assert.fail("Exception when testing persistance layer "+e.getMessage());
				}

				doneSignal.countDown();
			}).start();
		}

		try {
			doneSignal.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return timeStamps;
	}
}
