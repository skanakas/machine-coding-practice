package com.booking.movie.model;

import lombok.Getter;

@Getter
public class SeatLock {
	
	private Seat seat;
	private Show show;
	private User lockedBy;
	private long expireAt;
	private long createdAt;
	
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SeatLock [seat=").append(seat).append(", show=").append(show).append(", lockedBy=")
				.append(lockedBy).append(", expireAt=").append(expireAt).append(", createdAt=").append(createdAt)
				.append("]");
		return builder.toString();
	}



	public SeatLock(Seat seat, Show show, User lockedBy, long expireAt, long createdAt) {
		super();
		this.seat = seat;
		this.show = show;
		this.lockedBy = lockedBy;
		this.expireAt = expireAt;
		this.createdAt = createdAt;
		
		if(this.expireAt <= this.createdAt)
			throw new RuntimeException("INVALID INPUT");
	}
	
}
