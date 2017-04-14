package com.mideas.rpg.v2.utils;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.utils.render.Draw;
import com.mideas.rpg.v2.utils.render.Sprites;

public class CrossButton {

	public final static int DEFAULT_WIDTH = 22;
	public final static int DEFAULT_HEIGHT = 19;
	private float x;
	private float y;
	private float x_size;
	private float y_size;
	private Texture texture = Sprites.cross_button;
	private boolean buttonDown;
	private boolean buttonHover;
	private boolean hasClicked;
	
	public CrossButton(float x, float y, float x_size, float y_size) {
		this.x = x;
		this.y = y;
		this.x_size = x_size;
		this.y_size = y_size;
	}
	
	public CrossButton(float x, float y) {
		this(x, y, DEFAULT_WIDTH*Mideas.getDisplayXFactor(), DEFAULT_HEIGHT*Mideas.getDisplayYFactor());
	}
	
	public void draw(float x, float y) {
		if(!this.buttonDown && !this.buttonHover) {
			this.texture = Sprites.cross_button;
		}
		else if(this.buttonDown && !this.buttonHover) {
			this.texture = Sprites.cross_button_down;
		}
		Draw.drawQuad(this.texture, x, y, this.x_size, this.y_size);
	}
	
	public void draw() {
		draw(this.x, this.y);
	}
	
	public boolean event() {
		if(Mideas.getHover() && Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x+this.x_size && Mideas.mouseY() >= this.y && Mideas.mouseY() <= this.y+this.y_size) {
			this.texture = Sprites.cross_button_hover;
			this.buttonHover = true;
			Mideas.setHover(false);
		}
		else {
			this.buttonHover = false;
		}
		if(this.buttonHover) {
			if(Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
					this.texture = Sprites.cross_button_hover_down;
					this.buttonDown = true;
				}
			}
			else if(this.buttonDown) {
				if(Mouse.getEventButton() == 0) {
					this.buttonDown = false;
					this.texture = Sprites.cross_button_hover;
					eventButtonClick();
					this.hasClicked = true;
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
				this.hasClicked = false;
			}
		}
		if(this.buttonDown) {
			if(this.buttonHover) {
				this.texture = Sprites.cross_button_hover_down;
			}
		}
		return false;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setHoverFalse() {
		this.buttonHover = false;
	}
	
	public void setButtonWidth(float width) {
		this.x_size = width;
	}
	
	public void setButtonHeight(float height) {
		this.y_size = height;
	}
	
	protected void eventButtonClick() {}
	
	public boolean getButtonDown() {
		return this.buttonDown;
	}
	
	public boolean hasClicked() {
		return this.hasClicked;
	}
	
	public boolean isHover() {
		return this.buttonHover;
	}
	
	public void reset() {
		this.buttonDown = false;
		this.buttonHover = false;
		this.hasClicked = false;
		this.texture = Sprites.cross_button;
	}
	
	public void update(float x, float y, float x_size, float y_size) {
		this.x = x;
		this.y = y;
		this.x_size = x_size;
		this.y_size = y_size;
	}
	
	public void update(float x, float y) {
		this.x = x;
		this.y = y;
		this.x_size = DEFAULT_WIDTH*Mideas.getDisplayXFactor();
		this.y_size = DEFAULT_HEIGHT*Mideas.getDisplayYFactor();
	}
}