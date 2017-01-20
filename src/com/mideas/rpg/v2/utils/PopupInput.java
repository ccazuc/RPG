package com.mideas.rpg.v2.utils;

import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Mideas;

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
		this.textWidth = textFont.getWidth(this.message);
		this.cancelButton = new Button(this.x+this.x_size/2+10, this.y+this.y_size-37*Mideas.getDisplayYFactor(), BUTTON_WIDTH*Mideas.getDisplayXFactor(), BUTTON_HEIGHT*Mideas.getDisplayYFactor(), "No", 12, 1) {
			
			@Override
			public void eventButtonClick() {
				PopupInput.this.acceptButton.popupClosed();
				PopupInput.this.isActive = false;
			}
		};
		this.input = new Input(textFont, 30, false, this.x+this.x_size/2-input_bar_width*Mideas.getDisplayXFactor()/2+12*Mideas.getDisplayXFactor(), y+50*Mideas.getDisplayYFactor(), input_bar_width*Mideas.getDisplayXFactor(), 15) {
			
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
		if(!this.isActive) {
			return;
		}
		this.background.draw();
		this.inputBar.draw();
		this.input.draw();
		textFont.drawBegin();
		textFont.drawStringShadowPart(this.x+this.x_size/2-this.textWidth/2, this.y+15*Mideas.getDisplayYFactor(), this.message, Color.WHITE, Color.BLACK, 1, 0, 0);
		//FontManager.get("FRIZQT", 13).drawStringShadowPart(this.x+this.x_size/2-this.inputBar.getWidth()/2+13*Mideas.getDisplayXFactor(), this.y+51*Mideas.getDisplayYFactor(), this.input.getText(), Color.WHITE, Color.BLACK, 1, 0, 0);
		textFont.drawEnd();
		this.cancelButton.draw();
		this.acceptButton.draw();
	}
	
	@Override
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
		}
		if(this.inputBar.event()) {
			this.input.setIsActive(true);
			return true;
		}
		return false;
	}
	
	public boolean keyEvent() {
		if(this.isActive) {
			return this.input.event();
		}
		return false;
	}
	
	@Override
	public void popupClosed() {
		this.acceptButton.popupClosed();
		this.acceptButton.reset();
		this.cancelButton.reset();
		this.input.resetText();
		this.input.setIsActive(false);
		this.isActive = false;
	}

	public void setPopup(Button button, String msg, float input_bar_width, int inputMaxLength) {
		if(activatedPopup != null && activatedPopup.isActive) {
			activatedPopup.popupClosed();
		}
		if(input_bar_width*Mideas.getDisplayXFactor() > this.x_size) {
			this.x_size = (int)(input_bar_width*Mideas.getDisplayXFactor()+40*Mideas.getDisplayXFactor());
			this.x = Display.getWidth()/2-this.x_size/2;
			this.x_size_save = (int)input_bar_width+40;
			this.background.update(this.x, this.y, this.x_size, this.y_size);
		}
		this.input.setMaxLength(inputMaxLength);
		this.inputBar.setWidth(input_bar_width*Mideas.getDisplayXFactor());
		this.inputBar.setX(Display.getWidth()/2-this.inputBar.getWidth()/2);
		this.input.update(this.x+this.x_size/2-input_bar_width*Mideas.getDisplayXFactor()/2+12*Mideas.getDisplayXFactor(), this.y+50*Mideas.getDisplayYFactor(), input_bar_width*Mideas.getDisplayXFactor());
		this.x_size_input_bar_save = (int)input_bar_width;
		this.acceptButton = button;
		updateAcceptButton();
		setText(msg);
		onShow();
	}
	
	public void setInputText(String text) {
		this.input.setText(text);
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
		this.input.update(this.x+this.x_size/2-this.x_size_input_bar_save*Mideas.getDisplayXFactor()/2+12*Mideas.getDisplayXFactor(), y+50*Mideas.getDisplayYFactor(), this.x_size_input_bar_save*Mideas.getDisplayXFactor());
		this.background.update(this.x, this.y, this.x_size, this.y_size);
		this.cancelButton.update(this.x+this.x_size/2+10, this.y+this.y_size-37*Mideas.getDisplayYFactor(), BUTTON_WIDTH*Mideas.getDisplayXFactor(), BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		updateAcceptButton();
	}
	
	public Input getInput() {
		return this.input;
	}
}
