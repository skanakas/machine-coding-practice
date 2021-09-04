package com.digilock.model;

import lombok.Getter;

@Getter
public enum PackageSize {
	S(1),M(2),L(3),XL(4);

	int id = 0;
	private PackageSize(int id) {
		this.id = id;
	}
	
	public static PackageSize fromId(int id) {
		for(PackageSize e : values()) {
			if(e.id == id) return e;
		}
		return null;
	}
}
