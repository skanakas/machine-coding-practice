package com.booking.movie.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class Screen {
	
	String id;
	String name;
	Theater theater;
	List<Seat> seats;
	public Screen(String name, Theater theater) {
		super();
		this.id = UUID.randomUUID().toString();
		this.name = name;
		this.theater = theater;
		this.seats = new ArrayList<>();
	}
	
	public void addSeat(@NonNull final Seat seat) {
		this.seats.add(seat);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Screen [name=").append(name).append(", theater=").append(theater).append(", seats=")
				.append(seats).append("]");
		return builder.toString();
	}
	
}
