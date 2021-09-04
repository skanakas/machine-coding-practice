package com.digilock.model;

import java.util.ArrayList;
import java.util.List;

import com.digilock.model.common.GeoLocation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LockerLocation {
	private Integer locationGridId;
	private List<Locker> lockers = new ArrayList<>();
	private GeoLocation geoLocation;
}
