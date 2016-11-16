package com.mideas.rpg.v2.utils;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.TTF2;

import org.newdawn.slick.Color;

public class Popup {

	private int x;
	private int y;
	private int x_size;
	private int y_size;
	private AlertBackground background;
	private String message;
	protected Button acceptButton;
	private Button cancelButton;
	private int textWidth;
	protected boolean isActive;
	private final static int BUTTON_WIDTH = 135;
	private final static int BUTTON_HEIGHT = 24;
	
	public Popup(float x, float y, float x_size, float y_size, String message) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)x_size;
		this.y_size = (int)y_size;
		this.message = message;
		this.textWidth = TTF2.popup.getWidth(this.message);
		this.background = new AlertBackground(this.x, this.y, this.x_size, this.y_size, .7f);
		this.cancelButton = new Button(this.x+this.x_size/2+10, this.y+this.y_size-37*Mideas.getDisplayYFactor(), BUTTON_WIDTH*Mideas.getDisplayXFactor(), BUTTON_HEIGHT*Mideas.getDisplayYFactor(), "No", 12, 1) {
			
			@Override
			public void eventButtonClick() {
				Popup.this.acceptButton.popupClosed();
				Popup.this.isActive = false;
			}
		};
	}
	
	public void draw() {
		this.background.draw();
		TTF2.popup.drawStringShadow(this.x+this.x_size/2-this.textWidth/2, this.y+15*Mideas.getDisplayYFactor(), this.message, Color.white, Color.black, 1, 0, 0);
		this.cancelButton.draw();
		this.acceptButton.draw();
	}
	
	public boolean event() {
		return this.cancelButton.event() || this.acceptButton.event();
	}
	
	public void setPopup(Button button, String msg) {
		this.acceptButton = button;
		button.setButtonWidth(BUTTON_WIDTH*Mideas.getDisplayXFactor());
		button.setButtonHeight(BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		button.setX(this.x+this.x_size/2-10-BUTTON_WIDTH*Mideas.getDisplayXFactor());
		button.setY(this.y+this.y_size-37*Mideas.getDisplayYFactor());
		setText(msg);
	}
	
	public void setActive(boolean we) {
		if(!we) {
			this.acceptButton.popupClosed();
		}
		this.isActive = we;
	}
	
	public boolean isActive() {
		return this.isActive;
	}
	
	public void setText(String text) {
		this.message = text;
		this.textWidth = TTF2.popup.getWidth(text);
	}
	
	public void setAcceptButton(Button button) {
		this.acceptButton = button;
	}
	
	public void update(float x, float y, float x_size, float y_size) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)x_size;
		this.y_size = (int)y_size;
		this.background.update(this.x, this.y, this.x_size, this.y_size);
		this.cancelButton.update(this.x+this.x_size/2+10, this.y+this.y_size-37*Mideas.getDisplayYFactor(), BUTTON_WIDTH*Mideas.getDisplayXFactor(), BUTTON_HEIGHT*Mideas.getDisplayYFactor());
	}
}
