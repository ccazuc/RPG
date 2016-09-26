package com.mideas.rpg.v2.utils;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;

public class CheckBox {

	private float x;
	private float y;
	private String text;
	private String tooltip_text;
	private boolean mouseHover;
	private boolean leftButtonDown;
	private boolean rightButtonDown;
	
	public CheckBox(float x, float y, String text, String tooltip_text) {
		this.x = x;
		this.y = y;
		this.text = text;
		this.tooltip_text = tooltip_text;
	}
	
	public void draw() {
		if(this.mouseHover) {
			if(this.leftButtonDown || this.rightButtonDown) {
				Draw.drawQuad(Sprites.check_box_down_hover, this.x, this.y);
			}
			else {
				Draw.drawQuad(Sprites.check_box_hover, this.x, this.y);
			}
			//draw tooltip
		}
		else {
			if(this.leftButtonDown || this.rightButtonDown) {
				Draw.drawQuad(Sprites.check_box_down, this.x, this.y);
			}
			else {
				Draw.drawQuad(Sprites.check_box, this.x, this.y);
			}
		}
		if(get()) {
			Draw.drawQuad(Sprites.check_box_enable, this.x-1, this.y-1);
		}
		TTF2.checkBox.drawStringShadow(this.x+Sprites.check_box.getImageWidth()*Mideas.getDisplayXFactor()+10, this.y, this.text, Color.white, Color.black, 1, 1, 1);
	}
	
	public void event() {
		if(Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x+Sprites.check_box.getImageWidth()*Mideas.getDisplayXFactor()+10+TTF2.checkBox.getWidth(this.text) && Mideas.mouseY() >= this.y-3 && Mideas.mouseY() <= this.y+3+Sprites.check_box.getImageHeight()*Mideas.getDisplayXFactor()) {
			this.mouseHover = true;
			if(Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0) {
					this.leftButtonDown = true;
				}
				else if(Mouse.getEventButton() == 1) {
					this.rightButtonDown = true;
				}
			}
			else {
				if(Mouse.getEventButton() == 0) {
					set();
					this.leftButtonDown = false;
					this.rightButtonDown = false;
				}
				else if(Mouse.getEventButton() == 1) {
					this.rightButtonDown = false;
					this.leftButtonDown = false;
				}
			}
		}
		else {
			if(!Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
					this.rightButtonDown = false;
					this.leftButtonDown = false;
				}
			}
			this.mouseHover = false;
		}
	}
	
	public void updateSize() {
		
	}
	
	protected void set() {}
	protected boolean get() {return false;}
}
