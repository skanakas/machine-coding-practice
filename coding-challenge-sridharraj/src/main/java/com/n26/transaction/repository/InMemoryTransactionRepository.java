/**
 * 
 */
package com.n26.transaction.repository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.n26.transaction.model.Transaction;
import com.n26.transaction.model.TransactionsCacheObject;

/**
 * @author SridharRaj
 *
 */
public class InMemoryTransactionRepository extends SlidingWindowLogRepository implements TransactionRepository {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//Data store
	private final ConcurrentSkipListMap<Long, List<Transaction>> transactionPerMSec;
	private final BlockingQueue<TransactionsCacheObject> cacheEvictionQueue;
	
	//Resource lock
	private final ReentrantLock lock = new ReentrantLock();
	private final Condition newCacheKeyArrived = lock.newCondition();
	
	//Cleanup daemon
	private Thread cacheMonitorTask = null;
	
	public InMemoryTransactionRepository(int duration, ChronoUnit timeUnit) {
		super(duration, timeUnit);
		this.transactionPerMSec = new ConcurrentSkipListMap<>(); //To maintain the natural order on keys
		this.cacheEvictionQueue = new PriorityBlockingQueue<>(11, //Default size
				(c1, c2) -> (int) (c1.getExpireAt() - c2.getExpireAt()));
	}
	
	@PostConstruct
	public void postConstruct() {
		cacheMonitorTask = new Thread(cacheKeyAutoEvictionTask());
		cacheMonitorTask.setDaemon(true);
		cacheMonitorTask.start();
	}

	private Runnable cacheKeyAutoEvictionTask() {
		return ()->{
			while(!Thread.currentThread().isInterrupted()) {
				
				try {
					lock.lock();

					while(this.cacheEvictionQueue.isEmpty()) {
						newCacheKeyArrived.await();
					}

					long reminingTimeInMs = 0;
					while(!this.cacheEvictionQueue.isEmpty()) {

						reminingTimeInMs = this.cacheEvictionQueue.peek().getExpireAt() - Instant.now().toEpochMilli();

						if(reminingTimeInMs <= 0) {
							break;
						}

						newCacheKeyArrived.await(reminingTimeInMs, TimeUnit.MILLISECONDS);
					}
					
					TransactionsCacheObject cacheObject = this.cacheEvictionQueue.poll();
					if(cacheObject != null) {
						boolean removeStatus = this.transactionPerMSec.remove(cacheObject.getCacheKey(), cacheObject.getRefObject());
						if(logger.isInfoEnabled())
							logger.info("AUTO evict cache from in memory with key {} was {}", cacheObject.getCacheKey(), removeStatus?"SUCCESSFUL":"FAILED");
					}
					
				} catch (InterruptedException exception) {
					Thread.currentThread().interrupt();
				} finally {
					lock.unlock();
				}
			}
		};
	}
	
	@PreDestroy
    public void cleanup() {
		cacheMonitorTask.interrupt();
    }

	@Override
	public void saveTransaction(Transaction transaction) {
		
		lock.lock();
		try {
			long transactionTimeStamp = transaction.getTimestamp().toEpochMilli();
			if(this.transactionPerMSec.get(transactionTimeStamp) == null) {

				List<Transaction> transactions = new CopyOnWriteArrayList<>();
				Instant expiryTimeStamp = transaction.getTimestamp().plus(super.duration, super.timeUnit);
				TransactionsCacheObject cacheObject = new TransactionsCacheObject(transactionTimeStamp, transactions, expiryTimeStamp.toEpochMilli());
				cacheEvictionQueue.offer(cacheObject);
				if(logger.isDebugEnabled())
					logger.debug("New cache Key created for timestamp {}", transactionTimeStamp);

				newCacheKeyArrived.signal();

				this.transactionPerMSec.put(transactionTimeStamp, transactions);
			}
			this.transactionPerMSec.get(transactionTimeStamp).add(transaction);
		} finally { 
			lock.unlock();
		}
		
		if(logger.isDebugEnabled())
			logger.debug("Transaction persisted ", transaction.toString());
	}
	
	@Override
	public List<Transaction> getTransactionsBetween(Instant startWindow, Instant endWindow) {
		
		if(startWindow.isAfter(endWindow)) return null;
		
		Collection<List<Transaction>> slidingWindowEntities = this.transactionPerMSec
				.subMap(startWindow.toEpochMilli(), endWindow.toEpochMilli())
				.values();
		List<Transaction> transactionInCurrentWindow = slidingWindowEntities
				.stream()
				.flatMap(txn -> txn.stream())
				.collect(Collectors.toList());
		return transactionInCurrentWindow;
	}

	@Override
	public void deleteAll() {
		cacheEvictionQueue.clear();
		transactionPerMSec.clear();
	}

	@Override
	public List<Transaction> findByTimeStamp(Instant timeStamp) {
		return this.transactionPerMSec.get(timeStamp.toEpochMilli());
	}

}
