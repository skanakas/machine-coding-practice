package com.app.snakeandladder.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Ladder {
	private Integer start, end;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Ladder [start=").append(start).append(", end=").append(end).append("]");
		return builder.toString();
	}
	
	
}
