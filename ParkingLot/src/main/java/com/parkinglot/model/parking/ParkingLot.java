package com.parkinglot.model.parking;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.parkinglot.model.common.Address;

import lombok.Getter;
import lombok.Setter;

public class ParkingLot {
	
	private String parkingLotId;
	@Getter @Setter private Address address;
	
	private List<ParkingFloor> parkingFloors;
	private List<ExitPanel> exitPanels;
	private List<EntryPanel> entryPanels;
	
	private ParkingLot() {
		parkingLotId = UUID.randomUUID().toString();
		parkingFloors = new ArrayList<>();
		exitPanels = new ArrayList<>();
		entryPanels = new ArrayList<>();
	}
	
	private static class InstanceHolder{
		private static final ParkingLot INSTANCE = new ParkingLot();
	}
	public static ParkingLot getInstance() {
		return InstanceHolder.INSTANCE;
	}
	
}
