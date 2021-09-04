package com.digilock.model.common;

import java.util.HashMap;
import java.util.Map;

import com.digilock.model.LockerSize;
import com.digilock.model.PackageSize;

public class Mapper {
	
	private static Map<PackageSize, LockerSize> packageLockSizeMap = new HashMap<>(); 

	static {
		packageLockSizeMap.put(PackageSize.L, LockerSize.L);
		packageLockSizeMap.put(PackageSize.M, LockerSize.M);
	}
	
	public static LockerSize getLockerSizeFor(PackageSize size) {
		return packageLockSizeMap.get(size);
	}

}
