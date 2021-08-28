package com.mp.model.shipment;

import java.time.LocalDateTime;

import com.mp.model.constants.ShipmentStatus;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ShipmentLog {
	private LocalDateTime localDateTime;
	private ShipmentStatus shipmentStatus;
}
