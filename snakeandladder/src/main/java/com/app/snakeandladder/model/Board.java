package com.app.snakeandladder.model;

import java.util.Map;

import lombok.Getter;

@Getter
public class Board {
	
    private Integer size;
    private Integer start;
    private Integer end;
	private Map<Integer, Snake> snakes;
	private Map<Integer, Ladder> ladderes;
	
	public Board(int size, Map<Integer, Snake> snakes, Map<Integer, Ladder> ladderes) {
		this.size = size;
		this.snakes = snakes;
		this.ladderes = ladderes;
		this.start = 1;
		this.end = this.size;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Board [size=").append(size).append(", start=").append(start).append(", end=").append(end)
				.append(", snakes=").append(snakes).append(", ladderes=").append(ladderes).append("]");
		return builder.toString();
	}
	
}
