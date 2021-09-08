package com.n26.transaction.repository;

import java.time.temporal.ChronoUnit;

import lombok.Getter;

@Getter
public abstract class SlidingWindowLogRepository {

	protected final int duration;
	protected final ChronoUnit timeUnit;
	
	public SlidingWindowLogRepository(int duration, ChronoUnit timeUnit) {
		super();
		this.duration = duration;
		this.timeUnit = timeUnit;
	}
	
	
}
