package com.digilock.service;

import java.util.Optional;
import java.util.function.Predicate;

import com.digilock.exception.LockerNotFoundExeption;
import com.digilock.exception.NotAuthorizedException;
import com.digilock.model.Locker;
import com.digilock.model.LockerInstance;
import com.digilock.model.LockerLocation;
import com.digilock.model.LockerSize;
import com.digilock.model.LockerStatus;
import com.digilock.model.Pack;
import com.digilock.model.common.GeoLocation;
import com.digilock.repo.LockerInstanceRepo;
import com.digilock.repo.LockerLocationRepo;
import com.digilock.repo.LockerRepo;
import com.digilock.util.LockFinderFilters;

public class LockerService {
	
	private LockerRepo lockerRepo = null;
	private GeoLocationService geoService = null;
	
	public LockerService(LockerRepo lockerRepo, GeoLocationService locationService) {
		this.lockerRepo = lockerRepo;
		this.geoService = locationService;
	}
	
	public Locker getAvailableLocker(LockerSize lockerSize, GeoLocation userLocation) throws LockerNotFoundExeption {
		
		/**
		 * get Lockers near userLocation
		 */
		Integer geoGridLocation = geoService.getGeoGridID(userLocation);
		LockerLocation lockerLocation =  LockerLocationRepo.getLockerLocation(geoGridLocation);
		
		if(lockerLocation == null)
			throw new LockerNotFoundExeption("No locker found in desiredLocation");

		Optional<Locker> nearestLocker = lockerLocation.getLockers()
					.stream()
					.filter(LockFinderFilters.availableFilter)
					.filter(LockFinderFilters.lockerSizeMatchFilter(lockerSize))
					.findFirst();
		
		if(!nearestLocker.isPresent())
			throw new LockerNotFoundExeption("No Available lockers in nearestLocation");
		
		
		return nearestLocker.get();
	}
	
	public Pack unlockLocker(String lockerId, String otp) throws LockerNotFoundExeption, NotAuthorizedException {
		
		Optional<LockerInstance> lockerInstance = LockerInstanceRepo.getLockerByLockerId(lockerId);
		
		if(!lockerInstance.isPresent())
			throw new LockerNotFoundExeption("lockerId "+lockerId+" not found");
		
		if(!lockerInstance.get().verifyOPT(otp))
			throw new NotAuthorizedException("OTP mistatch");
		
		Locker locker = lockerRepo.getInUseLocker(lockerId);
		
		Pack pack = locker.getCurrentPack();
		System.out.println("Locker Accessed = "+locker);
		
		lockerRepo.freeLocker(lockerId);
		
		return pack;
	}

}
