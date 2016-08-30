package com.mideas.rpg.v2.hud;

public class Cast {

	private int length;
	private String name;
	
	public Cast(int length, String name) {
		this.length = length*1000;
		this.name = name;
	}
	
	public void endCastEvent() {
		
	}
	
	public int getLength() {
		return this.length;
	}
	
	public String getName() {
		return this.name;
	}
}
