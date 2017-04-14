package com.mideas.rpg.v2;

import com.mideas.rpg.v2.utils.render.Sprites;

public class ThreadSprites implements Runnable {

	int i;
	
	public ThreadSprites(int i) {
		this.i = i;
	}
	
	public void run() {
		if(this.i == 0) {
			Sprites.sprite();
			Sprites.initBG();
		}
		else if(this.i == 1) {
			Sprites.sprite2();
		}
		else if(this.i == 2) {
			Sprites.sprite8();
		}
		else if(this.i == 3) {
			Sprites.sprite9();
		}
		else if(this.i == 4) {
			Sprites.sprite10();
		}
	}

}
