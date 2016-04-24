package com.mideas.rpg.v2.game.item.shop;

import com.mideas.rpg.v2.game.item.stuff.Stuff;

public class Shop {

	private Stuff[] stuff = new Stuff[11];
	
	public Stuff[] getstuff() {
		return this.stuff;
	}
	
	public Stuff getStuff(int i) {
		if(i < this.stuff.length) {
			return this.stuff[i];
		}
		return null;
	}
	
	public void setStuff(int i, Stuff stuff) {
		if(i < this.stuff.length) {
			this.stuff[i] = stuff;
		}
	}
}

