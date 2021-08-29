package com.booking.movie.model;

import java.util.List;

public class Booking {
	
	public enum BookingStatus {
		INIT,
		CONFIRMED,
		EXPIRED;
	}
	
	private String id;
	private Show show;
	private List<Seat> seats;
	private String userID;
	private BookingStatus bookingStatus;
	private TransactionDetails paymentdetails;

}
