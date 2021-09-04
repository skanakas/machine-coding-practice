package com.digilock.util;

import java.util.function.Predicate;

import com.digilock.model.Locker;
import com.digilock.model.LockerSize;
import com.digilock.model.LockerStatus;

public class LockFinderFilters {

	public static Predicate<? super Locker> availableFilter = locker -> locker.getStatus() == LockerStatus.AVAILABLE;
	
	public static Predicate<? super Locker> lockerSizeMatchFilter(LockerSize lockerSize){
		return locker -> locker.getLockerSize() == lockerSize;
	}
	
}
