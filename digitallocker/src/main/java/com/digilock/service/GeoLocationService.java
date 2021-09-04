package com.digilock.service;

import java.util.Random;

import com.digilock.model.common.GeoLocation;

public class GeoLocationService {

	public Integer getGeoGridID(GeoLocation location) {
		Random random = new Random();
		return random.nextInt(100);
	}
}
