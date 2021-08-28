package com.app.snakeandladder.model;

import org.apache.commons.lang3.RandomUtils;

import lombok.Getter;

public class Dice {
	
	@Getter private Integer sides;
	private int min;
	private int max;
	private Dice(int sides) {
		this.sides = sides;
		this.min = 0;
		this.max = sides+1;
	}
	
	public Integer roll() {
		int nextInt = RandomUtils.nextInt(min, max);
		return nextInt;
	}
	
	public static Dice createInstance(int sides) {
		return new Dice(sides);
	}
}
