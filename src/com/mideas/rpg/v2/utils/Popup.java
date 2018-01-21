package com.mideas.rpg.v2.utils;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.utils.render.TTF;
import com.mideas.rpg.v2.FontManager;

public class Popup {

	protected int x;
	protected int y;
	protected int x_size;
	protected int y_size;
	protected AlertBackground background;
	protected String message;
	protected Button acceptButton;
	protected Button cancelButton;
	protected int textWidth;
	protected boolean isActive;
	protected final static short BUTTON_WIDTH = 135;
	protected final static short BUTTON_HEIGHT = 24;
	protected final static byte TEXT_FONT_SIZE = 13;
	protected final static TTF textFont = FontManager.get("FRIZQT", TEXT_FONT_SIZE);
	protected final static String YES = "Yes";
	protected final static String NO = "No";
	protected final static String ACCEPT = "Accept";
	protected final static String DECLINE = "Decline";
	protected static Popup activatedPopup;
	
	public Popup(float x, float y, float x_size, float y_size, String message) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)x_size;
		this.y_size = (int)y_size;
		this.message = message;
		this.textWidth = textFont.getWidth(this.message);
		this.background = new AlertBackground(this.x, this.y, this.x_size, this.y_size, .7f);
		this.cancelButton = new Button(this.x+this.x_size/2+10, this.y+this.y_size-37*Mideas.getDisplayYFactor(), BUTTON_WIDTH, BUTTON_HEIGHT, "No", 12, 1) {
			
			@Override
			public void onLeftClickUp() {
				Popup.this.acceptButton.popupClosed();
				Popup.this.isActive = false;
			}
		};
	}
	
	public void draw() {
		if(!this.isActive) {
			return;
		}
		this.background.draw();
		textFont.drawStringShadow(this.x+this.x_size/2-this.textWidth/2, this.y+15*Mideas.getDisplayYFactor(), this.message, Color.WHITE, Color.BLACK, 1, 0, 0);
		this.cancelButton.draw();
		this.acceptButton.draw();
	}
	
	public boolean event() {
		if(!this.isActive) {
			return false;
		}
		if(this.cancelButton.event()) {
			this.cancelButton.reset();
			this.setActive(false);
			return true;
		}
		if(this.acceptButton.event()) {
			this.acceptButton.reset();
			this.isActive = false;
			return true;
		}
		return false;
	}
	
	public void setPopup(Button button, String msg) {
		if(activatedPopup != null && activatedPopup.isActive) {
			activatedPopup.popupClosed();
		}
		this.acceptButton = button;
		updateAcceptButton();
		setText(msg);
		this.isActive = true;
		activatedPopup = this;
	}
	
	public void setActive(boolean we) {
		if(!we) {
			popupClosed();
		}
		this.isActive = we;
	}
	
	public boolean isActive() {
		return this.isActive;
	}
	
	public void setText(String text) {
		this.message = text;
		this.textWidth = textFont.getWidth(text);
	}
	
	public void update(float x, float y, float x_size, float y_size) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)x_size;
		this.y_size = (int)y_size;
		this.background.update(this.x, this.y, this.x_size, this.y_size);
		this.cancelButton.update(this.x+this.x_size/2+10, this.y+this.y_size-37*Mideas.getDisplayYFactor());
		updateAcceptButton();
	}
	
	public void setTextTypeAccept() {
		this.acceptButton.setText(ACCEPT);
		this.cancelButton.setText(DECLINE);
	}
	
	public void setTextTypeYes() {
		this.acceptButton.setText(YES);
		this.cancelButton.setText(NO);
	}
	
	protected void updateAcceptButton() {
		if(this.acceptButton != null) {
			this.acceptButton.update(this.x+this.x_size/2-10-BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.y+this.y_size-37*Mideas.getDisplayYFactor());
		}
	}
	
	protected void popupClosed() {
		this.acceptButton.popupClosed();
		this.acceptButton.reset();
		this.cancelButton.reset();
		this.isActive = false;
	}
}