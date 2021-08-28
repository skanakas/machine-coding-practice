package com.app.snakeandladder.service;

import java.util.List;

import com.app.snakeandladder.model.Dice;

public class DiceService {
	
	private static DiceService INSTANCE = null;
	private DiceService() {
	}
	
	public static DiceService getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new DiceService();
		}
		return INSTANCE;
	}
	
	public Integer rollDice(final List<Dice> dice) {
		while(true) {
			int sum = dice.stream().map(d -> d.roll()).reduce(0, (a,b)->a+b);
			if(sum > 0)
				return sum;
		}
	}

}
