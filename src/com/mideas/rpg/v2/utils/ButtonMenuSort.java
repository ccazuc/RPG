package com.mideas.rpg.v2.utils;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF;
import com.mideas.rpg.v2.FontManager;

public class ButtonMenuSort {

	private int x;
	private int y;
	private int x_size;
	private String text;
	private boolean buttonHover;
	private boolean buttonDown;
	private TTF font;
	
	public ButtonMenuSort(float x, float y, float x_size, String text, float font_size) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)x_size;
		this.text = text;
		this.font = FontManager.get("FRIZQT", font_size);
	}
	
	public void draw() {
		int imageWidth = Sprites.button_menu_sort_left_part.getImageWidth();
		int imageHeight = (int)(Sprites.button_menu_sort_left_part.getImageHeight()*Mideas.getDisplayYFactor());
		Draw.drawQuad(Sprites.button_menu_sort_left_part, this.x, this.y, imageWidth, imageHeight);
		Draw.drawQuad(Sprites.button_menu_sort_middle_part, this.x+imageWidth, this.y, this.x_size-2*imageWidth, imageHeight);
		Draw.drawQuad(Sprites.button_menu_sort_right_part, this.x+this.x_size-imageWidth, this.y, imageWidth, imageHeight);
		if(this.buttonDown) {
			this.font.drawStringShadow(this.x+9*Mideas.getDisplayXFactor()+2, this.y+imageHeight/2-this.font.getLineHeight()/2+2, this.text, Color.white, Color.black, 1, 0, 0);
		}
		else {
			this.font.drawStringShadow(this.x+9*Mideas.getDisplayXFactor(), this.y+imageHeight/2-this.font.getLineHeight()/2, this.text, Color.white, Color.black, 1, 0, 0);
		}
		if(this.buttonHover) {
			Draw.drawQuadBlend(Sprites.button_menu_hover, this.x-3*Mideas.getDisplayXFactor(), this.y-3*Mideas.getDisplayYFactor(), this.x_size+7*Mideas.getDisplayYFactor(), imageHeight+9*Mideas.getDisplayYFactor());
		}
	}
	
	public boolean event() {
		this.buttonHover = false;
		if(Mideas.getHover() && Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x+this.x_size && Mideas.mouseY() >= this.y && Mideas.mouseY() <= this.y+Sprites.button_menu_sort_left_part.getImageHeight()*Mideas.getDisplayYFactor()) {
			this.buttonHover = true;
			Mideas.setHover(false);
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
					eventButtonClick();
					return true;
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
		return false;
	}
	
	public void update(float x, float y, float x_size) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)x_size;
	}
	
	protected void eventButtonClick() {}
 } 
