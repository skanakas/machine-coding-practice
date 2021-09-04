package com.digilock.model;

import com.digilock.model.common.Address;
import com.digilock.model.common.GeoLocation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Delivery {

	private DeliveryOption deliveryOption;
	private GeoLocation deliveryLocation;
	private Address deliveryAddress;
}
