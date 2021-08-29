package com.booking.movie.model;

import java.util.UUID;

import lombok.Getter;

@Getter
public class Seat {
	
	String id;
	String row; 
	Integer seatNum;
	public Seat(String row, Integer seatNum) {
		super();
		this.id = UUID.randomUUID().toString();
		this.row = row;
		this.seatNum = seatNum;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Seat [row=").append(row).append(", seatNum=").append(seatNum).append("]");
		return builder.toString();
	}

}
