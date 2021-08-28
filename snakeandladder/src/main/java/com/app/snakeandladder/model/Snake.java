package com.app.snakeandladder.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Snake {
	
	private Integer head, tail;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Snake [head=").append(head).append(", tail=").append(tail).append("]");
		return builder.toString();
	}
	
	

}
