package com.mideas.rpg.v2.utils;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;

public class ScrollBar {

	private final float Y_ASCENSOR_DOWN_SHIFT = -16;
	private final float Y_ASCENSOR_UP_SHIFT = 17;
	private float x;
	private float y;
	private float y_size;
	private float y_ascensor = this.Y_ASCENSOR_UP_SHIFT;
	private float y_ascensor_onclick = this.Y_ASCENSOR_UP_SHIFT;
	private float y_ascensor_lastclick = this.Y_ASCENSOR_UP_SHIFT;
	private boolean down;
	
	public ScrollBar(float x, float y, float y_size) {
		this.x = x;
		this.y = y;
		this.y_size = y_size;
	}
	
	public void draw() {
		drawUpArrow();
		drawDownArrow();
		Draw.drawQuad(Sprites.ascensor, this.x+4, this.y+this.y_ascensor);
	}
	
	public void event() {
		if(Mideas.mouseX() >= this.x+4 && Mideas.mouseX() <= this.x+24) {
			if(Mideas.mouseY() >= this.y+this.y_ascensor && Mideas.mouseY() <= this.y+this.y_ascensor+16) {
				if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
					if(Mouse.getEventButtonState()) {
						this.down = true;
						this.y_ascensor_onclick = Mideas.mouseY();
					}
				}
			}
		}
		if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
			if(!Mouse.getEventButtonState()) {
				this.down = false;
				this.y_ascensor_lastclick = this.y_ascensor;
			}
		}
		if(this.down) {
			if(Mideas.mouseY() >= this.y+this.Y_ASCENSOR_UP_SHIFT+7 && Mideas.mouseY() <= this.y+this.y_size+7+this.Y_ASCENSOR_DOWN_SHIFT*Mideas.getDisplayXFactor()) {
				this.y_ascensor = Mideas.mouseY()-this.y_ascensor_onclick+this.y_ascensor_lastclick;
			}
			else if(Mideas.mouseY() <= this.y+this.Y_ASCENSOR_UP_SHIFT) {
				this.y_ascensor = this.Y_ASCENSOR_UP_SHIFT;
			}
			else if(Mideas.mouseY() >= this.y+this.y_size+this.Y_ASCENSOR_DOWN_SHIFT*Mideas.getDisplayXFactor()) {
				this.y_ascensor = this.y_size+this.Y_ASCENSOR_DOWN_SHIFT*Mideas.getDisplayXFactor();
			}
		}
	}
	
	private void drawUpArrow() {
		if(this.y_ascensor == this.Y_ASCENSOR_UP_SHIFT) {
			Draw.drawQuad(Sprites.scrollbar_grey_up_arrow, this.x+4, this.y+2);
		}
		else {
			Draw.drawQuad(Sprites.scrollbar_up_arrow, this.x+4, this.y+2);
		}
	}
	
	private void drawDownArrow() {
		if(this.y_ascensor == this.y_size+this.Y_ASCENSOR_DOWN_SHIFT*Mideas.getDisplayXFactor()) {
			Draw.drawQuad(Sprites.scrollbar_grey_down_arrow, this.x+4, this.y+this.y_size+2);
		}
		else {
			Draw.drawQuad(Sprites.scrollbar_down_arrow, this.x+4, this.y+this.y_size+2);
		}
	}
}
