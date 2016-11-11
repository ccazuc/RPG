package com.mideas.rpg.v2.utils;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

public class EditBox {

	private Input input;
	private int x;
	private int y;
	private int x_size;
	private TTF font;
	private boolean buttonDown;
	private boolean buttonHover;
	
	public EditBox(float x, float y, float x_size, int maxLength, boolean debugActive, TTF font) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)x_size;
		this.input = new Input(font, maxLength, false, false) {
			
			@Override
			public boolean keyEvent(char c) {
				if(c == 27 || c == 13) { //escape or enter pressed
					this.setIsActive(false);
					return true;
				}
				return false;
			}
		};
		this.font = font;
	}
	
	public void draw() {
		int imageWidth = Sprites.edit_box_left_border.getImageWidth();
		int imageHeight = (int)(Sprites.edit_box_left_border.getImageHeight()*Mideas.getDisplayYFactor());
		Draw.drawQuad(Sprites.edit_box_left_border, this.x, this.y, imageWidth, imageHeight);
		Draw.drawQuad(Sprites.edit_box_middle_border, this.x+imageWidth, this.y, this.x_size-2*imageWidth, imageHeight);
		Draw.drawQuad(Sprites.edit_box_right_border, this.x+this.x_size-imageWidth, imageWidth, imageHeight);
		this.font.drawStringShadow(this.x, this.y, this.input.getText(), Color.white, Color.black, 1, 0, 0);
		if(this.input.isActive() && System.currentTimeMillis()%1000 < 500) {
			Draw.drawColorQuad(this.x+this.font.getWidth(this.input.getText()), this.y, 4*Mideas.getDisplayXFactor(), 10*Mideas.getDisplayYFactor(), Color.white);
		}
	}
	
	public void event() {
		this.input.event();
		this.buttonHover = false;
		if(Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x+this.x_size && Mideas.mouseY() >= this.y && Mideas.mouseY() <= this.y+Sprites.edit_box_left_border.getImageHeight()*Mideas.getDisplayYFactor()) {
			this.buttonHover = true;
		}
		if(this.buttonHover) {
			if(Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
					this.buttonDown = true;
				}
			}
			else if(this.buttonDown) {
				if(Mouse.getEventButton() == 0) {
					this.buttonDown = false;
					this.input.setIsActive(true);
				}
				else if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
					this.buttonDown = false;
				}
			}
		}
		else if(!Mouse.getEventButtonState()) {
			if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
				this.buttonDown = false;
			}
		}
	}
}
