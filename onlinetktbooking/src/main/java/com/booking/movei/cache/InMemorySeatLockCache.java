package com.booking.movei.cache;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import com.booking.movei.exception.SeatTempNotAvailableException;
import com.booking.movie.model.Seat;
import com.booking.movie.model.SeatLock;
import com.booking.movie.model.Show;
import com.booking.movie.model.User;
import com.booking.movie.repo.TheaterRepository;

public class InMemorySeatLockCache implements SeatLockProvider{

	/**
	 * Major Key : Show ID
	 * Minor Key : Seat Id
	 */
	private Map<String, Map<String, SeatLock>> seatLockMap = null;
	private BlockingQueue<SeatLock> seatLockQueue = null;
	private long lockDurationInSec = 0;
	private ReentrantLock lock = new ReentrantLock();
	private Condition newSeatLockArrived = lock.newCondition();

	public InMemorySeatLockCache(long lockDurationInSec) {
		this.lockDurationInSec = lockDurationInSec;
		this.seatLockMap = new ConcurrentHashMap<>();
		this.seatLockQueue = new PriorityBlockingQueue<SeatLock>(11, //default INIT size
				(l1,l2) -> (int)(l1.getExpireAt()-l2.getExpireAt()));
	}

	@Override
	public void lockSeats(Show show, List<Seat> seats, User user) {

		for(Seat seat : seats) {
			if(isSeatLocked(seat, show))
				throw new SeatTempNotAvailableException("Seat "+seat.getRow()+"_"+seat.getSeatNum()+" temp not available");
		}
		
		Long currTimeStamp = System.currentTimeMillis();
		Long expireAtTimeStamp = currTimeStamp + lockDurationInSec*1000;
		
		for(Seat seat : seats) {
			lock.lock();
			
			lockSeat(show, seat, user, expireAtTimeStamp, currTimeStamp);
			newSeatLockArrived.signal();
			
			lock.unlock();
		}
	}
	
	
	private void lockSeat(Show show, Seat seat, User user, long expireAt, long nowTs) {
		if(!seatLockMap.containsKey(show.getId())) {
			seatLockMap.put(show.getId(), new ConcurrentHashMap<>());
		}
		
		final SeatLock seatLock = new SeatLock(seat, show, user, expireAt, nowTs);
		this.seatLockMap.get(show.getId()).put(seat.getId(), seatLock);
		
		this.seatLockQueue.offer(seatLock);
	}

	private boolean isSeatLocked(Seat seat, Show show) {
		if(seatLockMap.get(show.getId()) != null && seatLockMap.get(show.getId()).get(seat.getId()) != null)
			return true;

		return false;
	}

	@Override
	public void unlockSeats(final Show show, final List<Seat> seats, final User user) {
		
		if(seatLockMap.get(show.getId()) == null)
			return;
		
		seats.forEach(seat -> {
			String seatID = seat.getId();
			seatLockMap.get(show.getId()).remove(seatID);
		});
		
	}
	
	@Override
	public boolean validateLock(Show show, Seat seat, User user) {
		return isSeatLocked(seat, show) && seatLockMap.get(show.getId()).get(seat.getId()).getLockedBy().equals(user);
	}
	
	@Override
	public List<Seat> getLockedSeats(Show show) {
		
		if(this.seatLockMap.get(show.getId()) == null)
			return Collections.unmodifiableList(Collections.emptyList()); //Immutable
		
		return this.seatLockMap.get(show.getId())
				.entrySet()
				.stream()
				.map(showEntry -> showEntry.getKey())
				.map(TheaterRepository.INSTANCE::getSeat)
				.collect(Collectors.toList());
	}
	
	
	@Override
	public void executeAutoEvictPolicy() throws InterruptedException {
		
		while(true) {
			lock.lock();
			
			while(this.seatLockQueue.isEmpty()) {
				newSeatLockArrived.await();
			}
			
			long remainingTimeInSec = 0;
			while(!seatLockQueue.isEmpty()) {
				
				remainingTimeInSec = (seatLockQueue.peek().getExpireAt())-System.currentTimeMillis();
				
				if(remainingTimeInSec<=0)
					break;
				
				newSeatLockArrived.await(remainingTimeInSec, TimeUnit.MILLISECONDS);
			}
			
			SeatLock cb = seatLockQueue.poll();
			
			if(this.validateLock(cb.getShow(), cb.getSeat(), cb.getLockedBy())) {
				seatLockMap.get(cb.getShow().getId()).remove(cb.getSeat().getId()); // unlock
				System.out.println("*************     AUTO Unlock the seat - "+cb+"    ****************");
			} else {
				System.out.println("****    Lock Already removed = "+cb+"  and hence ignoreing      *** ");
			}
			
			lock.unlock();
		}
		
	}

}
