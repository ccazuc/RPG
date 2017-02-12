package com.mideas.rpg.v2.utils;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;

public class EditBoxCrossButton {


	private short x;
	private short y;
	private short x_size;
	private short y_size;
	private Texture texture;
	private boolean buttonDown;
	private boolean buttonHover;
	private boolean isEnable = true;

	public EditBoxCrossButton(float x, float y) {
		this.x = (short)x;
		this.y = (short)y;
		this.x_size = (short)(Sprites.edit_box_cross_button.getImageWidth()*Mideas.getDisplayXFactor());
		this.y_size = (short)(Sprites.edit_box_cross_button.getImageHeight()*Mideas.getDisplayYFactor());
		this.texture = Sprites.edit_box_cross_button;
	}
	
	public void draw() {
		if(!this.isEnable) {
			return;
		}
		if(!activateCondition()) {
			return;
		}
		if(this.buttonDown) {
			Draw.drawQuad(this.texture, this.x+1, this.y+1, this.x_size, this.y_size);
		}
		else {
			Draw.drawQuad(this.texture, this.x, this.y, this.x_size, this.y_size);
		}
	}
	
	public boolean event() {
		if(!activateCondition()) {
			this.buttonHover = false;
			return false;
		}
		if(Mideas.getHover() && Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x+this.x_size && Mideas.mouseY() >= this.y && Mideas.mouseY() <= this.y+this.y_size) {
			this.buttonHover = true;
			Mideas.setHover(false);
		}
		else {
			this.buttonHover = false;
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
					this.buttonHover = false;
					this.texture = Sprites.edit_box_cross_button;
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
		if(this.buttonHover) {
			this.texture = Sprites.edit_box_cross_button_hover;
		}
		else {
			this.texture = Sprites.edit_box_cross_button;
		}
		return false;
	}
	
	protected void eventButtonClick() {}
	protected boolean activateCondition() {return true;}
	protected void popupClosed() {}
	
	public boolean getButtonDown() {
		return this.buttonDown;
	}
	public boolean isHover() {
		return this.buttonHover;
	}
	
	public void update(float x, float y) {
		this.x = (short)x;
		this.y = (short)y;
		this.x_size = (short)(Sprites.edit_box_cross_button.getImageWidth()*Mideas.getDisplayXFactor());
		this.y_size = (short)(Sprites.edit_box_cross_button.getImageHeight()*Mideas.getDisplayYFactor());
	}
}
