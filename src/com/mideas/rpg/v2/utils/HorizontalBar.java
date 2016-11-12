package com.mideas.rpg.v2.utils;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;

public class HorizontalBar {

	private int x;
	private int y;
	private int x_size;
	
	public HorizontalBar(float x, float y, float x_size) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)x_size;
	}
	
	public void draw() {
		int width = (int)(Sprites.horizontal_bar_left.getImageWidth()*Mideas.getDisplayXFactor());
		int height = (int)(Sprites.horizontal_bar_left.getImageHeight()*Mideas.getDisplayYFactor());
		Draw.drawQuad(Sprites.horizontal_bar_left, this.x, this.y, width, height);
		Draw.drawQuad(Sprites.horizontal_bar_middle, this.x+width, this.y, this.x_size-2*width, Sprites.horizontal_bar_middle.getImageHeight());
		Draw.drawQuad(Sprites.horizontal_bar_right, this.x+this.x_size-width, this.y, width, height);
	}
	
	public void update(float x, float y, float x_size) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)x_size;
	}
}
