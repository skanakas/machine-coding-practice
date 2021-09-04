package com.digilock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.digilock.model.Delivery;
import com.digilock.model.DeliveryOption;
import com.digilock.model.Locker;
import com.digilock.model.LockerLocation;
import com.digilock.model.LockerSize;
import com.digilock.model.Order;
import com.digilock.model.common.GeoLocation;
import com.digilock.repo.LockerRepo;
import com.digilock.service.GeoLocationService;

public class SampleTestData {

	private static GeoLocationService geoLocationService = new GeoLocationService();
	private static LockerRepo lockerRepo = new LockerRepo();
	private static Random random = new Random();

	public static List<LockerLocation> createLockers(int locationsCount){

		List<LockerLocation> lockersGroup = new ArrayList<>();

		while(locationsCount>0) {

			LockerLocation lockerLocation = new LockerLocation();
			GeoLocation geoLocation = new GeoLocation(random.nextLong(), random.nextLong());
			Integer locationID = geoLocationService.getGeoGridID(geoLocation);
			
			lockerLocation.setGeoLocation(geoLocation);
			lockerLocation.setLocationGridId(locationID);


			for (int i = 0; i < 50; i++) {
				Locker locker = new Locker(LockerSize.S, geoLocation, locationID);
				lockerRepo.addNewLocker(locker);
				lockerLocation.getLockers().add(locker);
			}
			for (int i = 0; i < 60; i++) {
				Locker locker = new Locker(LockerSize.M, geoLocation, locationID);
				lockerRepo.addNewLocker(locker);
				lockerLocation.getLockers().add(locker);
			}
			for (int i = 0; i < 20; i++) {
				Locker locker = new Locker(LockerSize.L, geoLocation, locationID);
				lockerRepo.addNewLocker(locker);
				lockerLocation.getLockers().add(locker);
			}
			for (int i = 0; i < 30; i++) {
				Locker locker = new Locker(LockerSize.XL, geoLocation, locationID);
				lockerRepo.addNewLocker(locker);
				lockerLocation.getLockers().add(locker);
			}

			lockersGroup.add(lockerLocation);

			locationsCount--;
		}

		return lockersGroup;
	}
	
	public static Order createSampleOrder() {
		
		GeoLocation geoLocation = new GeoLocation(random.nextLong(), random.nextLong());
		
		Delivery deliveryDetails = new Delivery(DeliveryOption.Locker, geoLocation, null);
		Order order = new Order(UUID.randomUUID().toString(), null, null, deliveryDetails );
		
		return order;
		
	}
	
	public static void main(String[] args) {
		createLockers(100);
	}

}
