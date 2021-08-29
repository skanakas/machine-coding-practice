package com.booking.movie.repo;
import java.util.HashMap;
import java.util.Map;

import com.booking.movei.exception.NotFoundException;
import com.booking.movie.model.Screen;
import com.booking.movie.model.Seat;
import com.booking.movie.model.Theater;

import lombok.NonNull;

public enum TheaterRepository {
	
	INSTANCE;
	
	private final Map<String, Theater> theaterMap = new HashMap<>();;
	private final Map<String, Screen> screensMap = new HashMap<>();
	private final Map<String, Seat> seatsMap = new HashMap<>();
	
	public Theater createTheater(@NonNull String theaterName) {
		Theater theater = new Theater(theaterName);
		this.theaterMap.put(theater.getId(), theater);
		return theater;
	}
	
	public Screen createScreenInTheater(String screenName, Theater theater) {
		Screen screen = new Screen(screenName, theater);
		this.screensMap.put(screen.getId(), screen);
		return screen;
	}
	
	public Seat createSeatInScreen(String row, Integer seatNum, Screen screen) {
		Seat seat = new Seat(row, seatNum);
		this.seatsMap.put(seat.getId(), seat);
		return seat;
	}
	
	public Seat getSeat(String seatId) {
		if(!this.seatsMap.containsKey(seatId))
			throw new NotFoundException(seatId+ "Seat Not found");
		return this.seatsMap.get(seatId);
	}

}
