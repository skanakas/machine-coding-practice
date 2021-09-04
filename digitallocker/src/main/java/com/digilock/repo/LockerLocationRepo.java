package com.digilock.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.digilock.model.LockerLocation;

public class LockerLocationRepo {
	
	private static List<LockerLocation> lockerLocations = new ArrayList<>();
	private static Map<Integer, LockerLocation> locationMap = new HashMap<>();
	private static int[][] locationMapGrid = new int[50][50];
	
    public static LockerLocation getLockerLocation(Integer geoGridLocation) {
        Optional<LockerLocation> lockerLocation =
                lockerLocations.stream()
                        .filter(ll -> ll.getLocationGridId().equals(geoGridLocation))
                        .findFirst();
        if (lockerLocation.isPresent())
            return lockerLocation.get();
        return null;
    }
    
    public static void addLockerLocation(LockerLocation location) {
    	lockerLocations.add(location);
    	locationMap.put(location.getLocationGridId(), location);
    }
    
    public static void postConstructGrid() {

    	for(int i = 0; i<50; i++) {
    		for(int j = 0; j <50; j++) {
    			if(locationMap.containsKey(i+j+2)) {
    				locationMapGrid[i][j] = 1;
    			}
    		}
    	}
    }

}
