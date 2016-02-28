package com.mideas.rpg.v2.game.stuff;

public class Bag {
	
	private Stuff[] bag = new Stuff[33];
	
	public Stuff[] getBag() {
		return bag;
	}
	
	public Stuff getBag(int i) {
		return bag[i];
	}
	
	public void setBag(int i, Stuff stuff) {
		bag[i] = stuff;
	}
}
