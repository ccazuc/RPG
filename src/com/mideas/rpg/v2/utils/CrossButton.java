package com.mideas.rpg.v2.utils;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;

public class CrossButton {

	private float x;
	private float y;
	private float x_size = Sprites.cross_button.getImageWidth()*Mideas.getDisplayXFactor();
	private float y_size = Sprites.cross_button.getImageHeight()*Mideas.getDisplayXFactor();
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
		this.x = x;
		this.y = y;
	}
	
	public void draw(float x, float y) {
		this.x = x;
		this.y = y;
		if(!this.buttonDown && !this.buttonHover) {
			this.texture = Sprites.cross_button;
		}
		else if(this.buttonDown && !this.buttonHover) {
			this.texture = Sprites.cross_button_down;
		}
		Draw.drawQuad(this.texture, x, y, this.x_size, this.y_size);
	}
	
	public void draw() {
		if(!this.buttonDown && !this.buttonHover) {
			this.texture = Sprites.cross_button;
		}
		else if(this.buttonDown && !this.buttonHover) {
			this.texture = Sprites.cross_button_down;
		}
		Draw.drawQuad(this.texture, this.x, this.y, this.x_size, this.y_size);
	}
	
	public void event() throws SQLException, NoSuchAlgorithmException {
		this.buttonHover = false;
		if(Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x+this.x_size && Mideas.mouseY() >= this.y && Mideas.mouseY() <= this.y+this.y_size) {
			this.texture = Sprites.cross_button_hover;
			this.buttonHover = true;
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
					return;
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
		this.x_size = width*Mideas.getDisplayXFactor();
	}
	
	public void setButtonHeight(float height) {
		this.y_size = height*Mideas.getDisplayXFactor();
	}
	
	@SuppressWarnings("unused")
	protected void eventButtonClick() throws NoSuchAlgorithmException, SQLException {}
	
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
	}
}