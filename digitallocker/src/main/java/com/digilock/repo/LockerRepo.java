package com.digilock.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import com.digilock.model.Locker;

public class LockerRepo {
	
	private static List<Locker> availableLockers = new CopyOnWriteArrayList<Locker>();
	private static Map<String, Locker> inUseLockers = new HashMap<>();
	
	public void addNewLocker(Locker locker) {
		availableLockers.add(locker);
	}
	
	public List<Locker> getAvailableLockers(){
		return  availableLockers;
	}
	
	public void freeLocker(String lockerID) {
		Locker locker = inUseLockers.remove(lockerID);
		locker.reset();
		availableLockers.add(locker);
	}
	
	public void moveToInUse(Locker locker) {
		availableLockers.remove(locker);
		inUseLockers.put(locker.getId(), locker);
	}
	
	public Locker getInUseLocker(String lockerID) {
		return inUseLockers.get(lockerID);
	}

}
