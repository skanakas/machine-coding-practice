package com.parkinglot.model.parking;

import java.util.HashMap;
import java.util.Map;

import com.parkinglot.model.vechile.VechileType;

public class Mapper {
	
	private static final Map<VechileType, ParkingSpotType> vechiletoParkingSpotMap = new HashMap<VechileType, ParkingSpotType>() {
		private static final long serialVersionUID = 1L;

		{
			put(VechileType.CAR, ParkingSpotType.CAR);
			put(VechileType.ELEC_MOTORBIKE, ParkingSpotType.EBIKE);
			put(VechileType.VAN, ParkingSpotType.LARGE);
			put(VechileType.MOTORBIKE, ParkingSpotType.MOTORBIKE);
		}
	};
	
	public static ParkingSpotType getParkingSpotType(VechileType vechileType) {
		return vechiletoParkingSpotMap.get(vechileType);
	}

}
