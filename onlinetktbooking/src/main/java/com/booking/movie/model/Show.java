package com.booking.movie.model;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Show {
	
	public enum ShowStatus {
		SCHEDULED, // planned
		OPEN, //open for booking
		CLOSED // no more booking allowed
	}
	
	String id;
	Movie movie;
	Screen screen;
	@Setter LocalDateTime startTime;
	@Setter LocalDateTime endTime;
	@Setter ShowStatus showStatus;
	public Show(Movie movie, Screen screen, LocalDateTime startTime, LocalDateTime endTime) {
		super();
		this.id = UUID.randomUUID().toString();
		this.movie = movie;
		this.screen = screen;
		this.startTime = startTime;
		this.endTime = endTime;
		this.showStatus = ShowStatus.SCHEDULED;
	}
	
	public Show(Movie movie, Screen screen, LocalDateTime startTime, LocalDateTime endTime, ShowStatus status) {
		super();
		this.id = UUID.randomUUID().toString();
		this.movie = movie;
		this.screen = screen;
		this.startTime = startTime;
		this.endTime = endTime;
		this.showStatus = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Show [movie=").append(movie).append(", screen=").append(screen).append(", startTime=")
				.append(startTime).append(", endTime=").append(endTime).append(", showStatus=").append(showStatus)
				.append("]");
		return builder.toString();
	}

}
