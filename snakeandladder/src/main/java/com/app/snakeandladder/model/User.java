package com.app.snakeandladder.model;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

public class User {
	@Getter private String id, name;
	@Getter private Integer currentPos;
	@Getter @Setter private boolean won;
	
	public User(String name) {
		this.id = UUID.randomUUID().toString();
		this.name = name;
		this.won = false;
		this.currentPos = 0;
	}
	
	public void updateCurrPos(Integer pos) {
		this.currentPos = pos;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=").append(id).append(", name=").append(name).append(", currentPos=").append(currentPos)
				.append(", won=").append(won).append("]");
		return builder.toString();
	}
}
