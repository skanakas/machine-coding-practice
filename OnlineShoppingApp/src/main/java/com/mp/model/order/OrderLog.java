package com.mp.model.order;

import java.time.LocalDateTime;

import com.mp.model.constants.OrderStatus;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderLog {
	
	private LocalDateTime localDateTime;
	private OrderStatus orderStatus;

}
