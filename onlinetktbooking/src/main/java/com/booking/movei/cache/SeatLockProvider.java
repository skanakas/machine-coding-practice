package com.booking.movei.cache;

import java.util.List;

import com.booking.movie.model.Seat;
import com.booking.movie.model.Show;
import com.booking.movie.model.User;

public interface SeatLockProvider {
	
	public void lockSeats(Show show, List<Seat> seats, User user);
	
	public void unlockSeats(Show show, List<Seat> seats, User user);
	
	public boolean validateLock(Show show, Seat seat, User user);
	
	public List<Seat> getLockedSeats(Show show);

	void executeAutoEvictPolicy() throws InterruptedException;

}
