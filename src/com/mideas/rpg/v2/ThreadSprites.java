
package com.mideas.rpg.v2;

import java.io.IOException;

public class ThreadSprites implements Runnable {

	int i;
	
	public ThreadSprites(int i) {
		this.i = i;
	}
	
	public void run() {
		if(this.i == 0) {
			try {
				Sprites.sprite();
				Sprites.initBG();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if(this.i == 1) {
			try {
				Sprites.sprite2();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if(this.i == 2) {
			try {
				Sprites.sprite8();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if(this.i == 3) {
			try {
				Sprites.sprite9();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if(this.i == 4) {
			try {
				Sprites.sprite10();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
