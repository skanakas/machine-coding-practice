package com.digilock.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.digilock.model.common.GeoLocation;
import com.digilock.service.common.SecretCodeGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Locker {
	
	private String id;
	private LockerSize lockerSize;
	private GeoLocation location;
	private Integer locationID;
	private LockerStatus status;
	private Pack currentPack;
	private String opt;
	private LocalDateTime lastTimeAccessed;
	
	public Locker(LockerSize lockerSize, GeoLocation location, Integer locationID) {
		super();
		this.id = UUID.randomUUID().toString();
		this.lockerSize = lockerSize;
		this.location = location;
		this.locationID = locationID;
		this.status = LockerStatus.AVAILABLE;
		this.opt = SecretCodeGenerator.generateSecretCode(8);
	}

	public void reset() {
		this.status = LockerStatus.AVAILABLE;
		this.opt = SecretCodeGenerator.generateSecretCode(8);
		currentPack = null;
	}
	
}
