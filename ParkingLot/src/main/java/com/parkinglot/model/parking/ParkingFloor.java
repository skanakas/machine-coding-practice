package com.parkinglot.model.parking;

import static com.parkinglot.model.parking.ParkingSpotType.*;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

import lombok.Getter;

public class ParkingFloor {
	
	@Getter
	private String floorID;
	
	@Getter
	private Map<ParkingSpotType, Deque<ParkingSpot>> availableSpotMap = new HashMap<>();
	private Map<String, ParkingSpot> usedParkingSpotMap = new HashMap<>();
	
	public ParkingFloor(String id) {
		this.floorID = id;
		this.availableSpotMap.put(ABLED, new ConcurrentLinkedDeque<>());
		this.availableSpotMap.put(CAR, new ConcurrentLinkedDeque<>());
		this.availableSpotMap.put(EBIKE, new ConcurrentLinkedDeque<>());
		this.availableSpotMap.put(LARGE, new ConcurrentLinkedDeque<>());
		this.availableSpotMap.put(ELECTRIC, new ConcurrentLinkedDeque<>());
		this.availableSpotMap.put(MOTORBIKE, new ConcurrentLinkedDeque<>());
	}
}
