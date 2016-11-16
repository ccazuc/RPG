package com.mideas.rpg.v2.utils;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;

public class InputBar {

	private int x;
	private int y;
	private int x_size;
	
	public InputBar(float x, float y, float x_size) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)x_size;
	}
	
	public void draw() {
		int imageWidth = (int)(Sprites.input_bar_left_border.getImageWidth()*Mideas.getDisplayXFactor());
		int imageHeight = (int)(Sprites.input_bar_left_border.getImageHeight()*Mideas.getDisplayYFactor());
		Draw.drawQuad(Sprites.input_bar_left_border, this.x, this.y);
		Draw.drawQuad(Sprites.input_bar_middle_border, this.x+imageWidth, this.y, this.x_size-2*imageWidth, imageHeight);
		Draw.drawQuad(Sprites.input_bar_right_border, this.x+this.x_size-imageWidth, this.y);
	}
	
	public boolean isHover() {
		if(Mideas.getHover() && Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x+this.x_size && Mideas.mouseY() >= this.y && Mideas.mouseY() <= this.y+Sprites.input_bar_left_border.getImageHeight()*Mideas.getDisplayYFactor()) {
			Mideas.setHover(false);
			return true;
		}
		return false;
	}
	
	public void setXSize(float x_size) {
		this.x_size = (int)x_size;
	}
	
	public void setY(float y) {
		this.y = (int)y;
	}
	
	public void update(float x, float y, float x_size) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)x_size;
	}
}
