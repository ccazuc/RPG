package com.mideas.rpg.v2.utils;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.TTF2;

public class PopupInput extends Popup {

	protected Input input;
	private InputBar inputBar;
	private int x_size_save;
	private int x_size_input_bar_save;
	
	public PopupInput(float x, float y, float x_size, float y_size, String message, float input_bar_width) {
		super(x, y, x_size*Mideas.getDisplayXFactor(), y_size, message);
		if(input_bar_width*Mideas.getDisplayXFactor() > x_size) {
			this.x_size = (int)(input_bar_width*Mideas.getDisplayXFactor()+40*Mideas.getDisplayXFactor());
			this.x_size_save = (int)input_bar_width+40;
		}
		else {
			this.x_size_save = (int)x_size;
		}
		this.x = Display.getWidth()/2-this.x_size/2;
		this.x_size_input_bar_save = (int)input_bar_width;
		this.textWidth = TTF2.popup.getWidth(this.message);
		this.cancelButton = new Button(this.x+this.x_size/2+10, this.y+this.y_size-37*Mideas.getDisplayYFactor(), BUTTON_WIDTH*Mideas.getDisplayXFactor(), BUTTON_HEIGHT*Mideas.getDisplayYFactor(), "No", 12, 1) {
			
			@Override
			public void eventButtonClick() {
				PopupInput.this.acceptButton.popupClosed();
				PopupInput.this.isActive = false;
			}
		};
		this.input = new Input(TTF2.popup, 30, false, false) {
			
			@Override
			public boolean keyEvent(char c) {
				if(c == Input.ENTER_CHAR_VALUE) {
					PopupInput.this.acceptButton.eventButtonClick();
					PopupInput.this.isActive = false;
					return true;
				}
				else if(c == Input.ESCAPE_CHAR_VALUE) {
					PopupInput.this.acceptButton.popupClosed();
					PopupInput.this.isActive = false;
					return true;
				}
 				return false;
			}
		};
		this.inputBar = new InputBar(x+this.x_size/2-input_bar_width*Mideas.getDisplayXFactor()/2, y+42*Mideas.getDisplayYFactor(), input_bar_width*Mideas.getDisplayXFactor());
	}
	
	@Override
	public void draw() {
		if(this.isActive) {
			this.background.draw();
			TTF2.popup.drawStringShadow(this.x+this.x_size/2-this.textWidth/2, this.y+15*Mideas.getDisplayYFactor(), this.message, Color.white, Color.black, 1, 0, 0);
			this.inputBar.draw();
			TTF2.popup.drawStringShadow(this.x+this.x_size/2-this.inputBar.getWidth()/2+13*Mideas.getDisplayXFactor(), this.y+51*Mideas.getDisplayYFactor(), this.input.getText(), Color.white, Color.black, 1, 0, 0);
			if(this.input.isActive() && System.currentTimeMillis()%1000 < 500) {
				Draw.drawColorQuad(this.x+this.x_size/2-this.inputBar.getWidth()/2+13*Mideas.getDisplayXFactor()+this.input.getCursorShift(), this.y+51*Mideas.getDisplayYFactor(), 5*Mideas.getDisplayXFactor(), 14*Mideas.getDisplayYFactor(), Color.white);
			}
			this.cancelButton.draw();
			this.acceptButton.draw();
		}
	}
	
	@Override
	public boolean event() {
		if(this.isActive) {
			if(this.cancelButton.event() || this.acceptButton.event()) {
				this.setActive(false);
				return true;
			}
			if(this.inputBar.event()) {
				this.input.setIsActive(true);
				return true;
			}
		}
		return false;
	}
	
	public boolean keyEvent() {
		if(this.isActive) {;
			return this.input.event();
		}
		return false;
	}
	
	@Override
	public void popupClosed() {
		System.out.println("Input closed");
		this.acceptButton.popupClosed();
		this.acceptButton.reset();
		this.cancelButton.reset();
		this.input.resetText();
		this.input.setIsActive(false);
		this.isActive = false;
	}

	public void setPopup(Button button, String msg, float input_bar_width) {
		if(activatedPopup != null && activatedPopup.isActive) {
			activatedPopup.popupClosed();
		}
		else if(this.isActive) {
			popupClosed();
		}
		System.out.println(input_bar_width*Mideas.getDisplayXFactor()+" "+this.x_size);
		if(input_bar_width*Mideas.getDisplayXFactor() > this.x_size-40*Mideas.getDisplayXFactor()) {
			this.x_size = (int)(input_bar_width*Mideas.getDisplayXFactor()+45*Mideas.getDisplayXFactor());
			this.x = Display.getWidth()/2-this.x_size/2;
			this.x_size_save = (int)input_bar_width+45;
			this.background.update(this.x, this.y, this.x_size, this.y_size);
		}
		System.out.println(input_bar_width*Mideas.getDisplayXFactor()+" "+this.x_size);
		this.inputBar.setWidth(input_bar_width*Mideas.getDisplayXFactor());
		this.inputBar.setX(Display.getWidth()/2-this.inputBar.getWidth()/2);
		this.x_size_input_bar_save = (int)input_bar_width;
		this.acceptButton = button;
		updateAcceptButton();
		setText(msg);
		onShow();
	}
	
	public void setInputText(String text) {
		this.input.setText(text);
	}
	
	public Input getInput() {
		return this.input;
	}
	
	private void onShow() {
		activatedPopup = this;
		this.isActive = true;
		this.input.resetText();
		this.input.setIsActive(true);
	}
	
	public void update(float y, float y_size) {
		this.y = (int)y;
		this.x_size = (int)(this.x_size_save*Mideas.getDisplayXFactor());
		this.x = Display.getWidth()/2-this.x_size/2;
		this.y_size = (int)y_size;
		this.inputBar.update(Display.getWidth()/2-this.x_size_input_bar_save*Mideas.getDisplayXFactor()/2, y+42*Mideas.getDisplayYFactor(), this.x_size_input_bar_save*Mideas.getDisplayXFactor());
		this.background.update(this.x, this.y, this.x_size, this.y_size);
		this.cancelButton.update(this.x+this.x_size/2+10, this.y+this.y_size-37*Mideas.getDisplayYFactor(), BUTTON_WIDTH*Mideas.getDisplayXFactor(), BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		updateAcceptButton();
	}
}
