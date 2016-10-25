package com.mideas.rpg.v2.utils;

import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;

public class Tooltip {

	private int x;
	private int y;
	private int x_size;
	private int y_size;
	private Color color;
	
	public Tooltip(float x, float y, float x_size, float y_size, float opacity) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)x_size;
		this.y_size = (int)y_size;
		this.color = new Color(0, 0, 0, opacity);
	}
	
	public void draw() {
		float xFac = Mideas.getDisplayXFactor();
		float yFac = Mideas.getDisplayYFactor();
		int imageWidth = (int) (Sprites.tooltip_top_left_corner.getImageWidth()*xFac);
		int imageHeight = (int) (Sprites.tooltip_top_left_corner.getImageHeight()*yFac);
		Draw.drawQuad(Sprites.tooltip_top_left_corner, this.x, this.y);
		Draw.drawQuad(Sprites.tooltip_top, this.x+imageWidth, this.y, this.x_size-2*imageWidth, imageHeight);
		Draw.drawQuad(Sprites.tooltip_top_right_corner, this.x+this.x_size-imageWidth, this.y);
		Draw.drawQuad(Sprites.tooltip_bot_left_corner, this.x, this.y+this.y_size-imageHeight);
		Draw.drawQuad(Sprites.tooltip_bot, this.x+imageWidth, this.y+this.y_size-imageHeight, this.x_size-2*imageWidth, imageHeight);
		Draw.drawQuad(Sprites.tooltip_bot_right_corner, this.x+this.x_size-imageWidth, this.y+this.y_size-imageHeight);
		Draw.drawQuad(Sprites.tooltip_left, this.x, this.y+imageHeight, imageWidth, this.y_size-2*imageHeight);
		Draw.drawQuad(Sprites.tooltip_right, this.x+this.x_size-imageWidth, this.y+imageHeight, imageWidth, this.y_size-2*imageHeight);
		Draw.drawColorQuad(this.x+imageWidth, this.y+imageHeight, this.x_size-2*imageWidth, this.y_size-2*imageHeight, this.color);
	}
	
	public boolean isHover() {
		return Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x+this.x_size && Mideas.mouseY() >= this.y && Mideas.mouseY() <= this.y+this.y_size;
	}
	
	public void setWidth(float width) {
		this.x_size = (int)width;
	}
	
	public void setHeight(float height) {
		this.y_size = (int)height;
	}
	
	public void setX(float x) {
		this.x = (int)x;
	}
	
	public void setY(float y) {
		this.y = (int)y;
	}
	
	public void update(float x, float y, float x_size, float y_size) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)x_size;
		this.y_size = (int)y_size;
	}
	
	public void updatePosition(float x, float y) {
		this.x = (int)x;
		this.y = (int)y;
	}

	
	public void updateSize(float x_size, float y_size) {
		this.x_size = (int)x_size;
		this.y_size = (int)y_size;
	}
}