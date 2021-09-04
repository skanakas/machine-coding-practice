package com.digilock.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.digilock.model.LockerInstance;

public class LockerInstanceRepo {
	public static List<LockerInstance> lockerInstances = new ArrayList<>();

	public static Optional<LockerInstance> getLockerByLockerId(String lockerId) {
		return lockerInstances.stream()
				.filter(l -> l.getLockerID().equals(lockerId))
				.findFirst();
	}
}
