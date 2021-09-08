package com.n26.transaction.repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.n26.transaction.model.Transaction;


/**
 * @author SridharRaj
 *
 */
@SpringBootTest
public class TestInMemoryTransactionRepository extends AbstractTestStatistics {
	
	private static final Logger logger = LoggerFactory.getLogger(TestInMemoryTransactionRepository.class);

	@Test
	public void testParallelPersistence() throws InterruptedException {

		Instant start = Instant.now();
		int requestCount = 200, transactionsPerReq = 200;
		Thread requestThread = new Thread(() -> {
			try {
				sendParallelRequests(requestCount, transactionsPerReq);
			} catch (InterruptedException e) {
				Assert.fail("Exception when testing persistance layer");
			}
		});
		requestThread.start();
		requestThread.join();

		Instant end = Instant.now();

		int totalExpectedTransactions = (requestCount * transactionsPerReq);
		List<Transaction> transactionsPersisted = super.transactionRepository.getTransactionsBetween(start, end);
		Assert.assertEquals("All the requested transactions are not persisted ",totalExpectedTransactions, transactionsPersisted.size());

		Long sleepDuration = getCleanUpCycleDuration();
		logger.debug("Waiting for {} ms for clean up job to trigger", sleepDuration);
		Thread.sleep(sleepDuration);

		transactionsPersisted = super.transactionRepository.getTransactionsBetween(start, end);
		Assert.assertEquals("Cache Cleanup was not successful",0, transactionsPersisted.size());
	}
	
	@Test
	public void testRollingWindowEvictionEndToEnd() throws InterruptedException {

		Instant start = Instant.now();
		Thread requestThread = new Thread(() -> {
			try {
				sendParallelRequests(200, 200);
			} catch (InterruptedException e) {
				Assert.fail("Exception when testing persistance layer");
			}
		});
		requestThread.start();
		requestThread.join();
		Instant end = Instant.now();
		
		int totalExpectedTransactions = (200 * 200);
		List<Transaction> transactionsPersisted = super.transactionRepository.getTransactionsBetween(start, end);
		int currentKeySetSize = transactionsPersisted.size();
		Assert.assertEquals("All the requested transactions are not persisted ",totalExpectedTransactions, currentKeySetSize);

		Long sleepDuration = getCleanUpCycleDuration();
		logger.debug("****Sleeping for {} ms to resume more persistence", sleepDuration/2);
		Thread.sleep(sleepDuration/2); //Sleep for sometime and again start persisting
		
		/**
		 * Resume persistence
		 */
		requestThread = new Thread(() -> {
			try {
				sendParallelRequests(100, 50);
			} catch (InterruptedException e) {
				Assert.fail("Exception when testing persistance layer");
			}
		});
		requestThread.start();
		requestThread.join();
		end = Instant.now();
		
		Thread.sleep(sleepDuration/2); //Sleep
		logger.debug("****Sleeping for {} ms to resume the test case ", sleepDuration/2);
		transactionsPersisted = super.transactionRepository.getTransactionsBetween(start, end);
		Assert.assertNotEquals("Cache cycle has cleared wrong window keys... Issue in cache clean up",0, transactionsPersisted.size());
		
		int totalPersitedKeysInTestCase = totalExpectedTransactions + (10*50);
		Assert.assertTrue("Previous Window Keys are not cleaned up", (transactionsPersisted.size() < totalPersitedKeysInTestCase));
	}
	
	@Test
	public void testDeleteOperation() throws InterruptedException {

		Instant start = Instant.now();
		int requestCount = 200, transactionsPerReq = 200;
		Thread requestThread = new Thread(() -> {
			try {
				sendParallelRequests(requestCount, transactionsPerReq);
			} catch (InterruptedException e) {
				Assert.fail("Exception when testing persistance layer");
			}
		});
		requestThread.start();
		requestThread.join();

		Instant end = Instant.now();

		int totalExpectedTransactions = (requestCount * transactionsPerReq);
		List<Transaction> transactionsPersisted = super.transactionRepository.getTransactionsBetween(start, end);
		Assert.assertEquals("All the requested transactions are not persisted ",totalExpectedTransactions, transactionsPersisted.size());
		
		super.transactionRepository.deleteAll();

		transactionsPersisted = super.transactionRepository.getTransactionsBetween(start, end);
		Assert.assertEquals("Purge Data failed ",0, transactionsPersisted.size());
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
						super.transactionRepository.saveTransaction(transaction);
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
			Assert.fail("Exception in testing test cases"+e.getMessage());
		}
		return timeStamps;
	}
}
