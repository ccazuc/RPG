package com.mideas.rpg.v2.game.item.shop;

import com.mideas.rpg.v2.game.item.stuff.Stuff;

public class Shop {

	private Stuff[] stuff = new Stuff[11];
	
	public Stuff[] getstuff() {
		return stuff;
	}
	
	public Stuff getStuff(int i) {
		return stuff[i];
	}
	
	public void setStuff(int i, Stuff stuff) {
		this.stuff[i] = stuff;
	}
}

