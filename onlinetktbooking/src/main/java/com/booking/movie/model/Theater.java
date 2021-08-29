package com.booking.movie.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class Theater {
	
	String id;
	String name;
	List<Screen> screens;
	public Theater(String name) {
		super();
		this.name = name;
		this.id = UUID.randomUUID().toString();
		this.screens = new ArrayList<>();
	}
	
	public void addScreen(@NonNull final Screen screen) {
		this.screens.add(screen);
	}

}
