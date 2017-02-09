package com.mideas.rpg.v2.utils;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF;

import org.lwjgl.input.Mouse;

public class EditBox {

	private final short textOffset;
	private final short cursorWidth;
	private final short cursorHeight;
	private final short inputMaxWidth;
	private final short xSizeSave;
	protected final Input input;
	private int x;
	private int y;
	private int x_size;
	private boolean buttonDown;
	private boolean buttonHover;
	
	public EditBox(float x, float y, float x_size, int inputMaxLength, float textOffset, float inputMaxWidth, TTF font, boolean isIntegerInput, int cursorWidth, int cursorHeight, String defaultText) {
		this.x = (int)x;
		this.y = (int)y;
		this.xSizeSave = (short)x_size;
		this.x_size = (int)(x_size*Mideas.getDisplayXFactor());
		this.textOffset = (short)textOffset;
		this.cursorWidth = (short)cursorWidth;
		this.cursorHeight = (short)cursorHeight;
		this.inputMaxWidth = (short)inputMaxWidth;
		if(isIntegerInput) {
			this.input = new IntegerInput(font, x+textOffset*Mideas.getDisplayXFactor(), y+3, inputMaxLength, inputMaxWidth*Mideas.getDisplayXFactor(), cursorWidth*Mideas.getDisplayXFactor(), cursorHeight*Mideas.getDisplayYFactor()) {
				
				@Override
				public boolean checkValue(int value) {
					return EditBox.this.checkValue(value);
				}
				
				@Override
				public boolean keyEvent(char c) {
					return EditBox.this.keyEvent(c);
				}
			};
		}
		else {
			this.input = new Input(font, inputMaxLength, false, x+textOffset*Mideas.getDisplayXFactor(), y+3, inputMaxWidth*Mideas.getDisplayXFactor(), cursorWidth*Mideas.getDisplayXFactor(), cursorHeight*Mideas.getDisplayYFactor(), defaultText) {
				
				@Override
				public boolean keyEvent(char c) {
					return EditBox.this.keyEvent(c);
				}
			};
		}
	}
	
	public EditBox(float x, float y, float x_size, int inputMaxLength, float textOffset, float inputMaxWidth, TTF font, boolean isIntegerInput) {
		this(x, y, x_size, inputMaxLength, textOffset, inputMaxWidth, font, isIntegerInput, 2, 13, "");
	}
	
	public void draw() {
		int imageWidth = Sprites.edit_box_left_border.getImageWidth();
		int imageHeight = (int)(Sprites.edit_box_left_border.getImageHeight()*Mideas.getDisplayYFactor());
		//Draw.drawColorQuad(this.x, this.y, this.x_size, imageHeight, this.bgColor);
		Draw.drawQuad(Sprites.edit_box_left_border, this.x, this.y, imageWidth, imageHeight);
		Draw.drawQuad(Sprites.edit_box_middle_border, this.x+imageWidth, this.y, this.x_size-2*imageWidth, imageHeight);
		Draw.drawQuad(Sprites.edit_box_right_border, this.x+this.x_size-imageWidth, this.y, imageWidth, imageHeight);
		//this.font.drawStringShadow(this.x+6, this.y+2, this.input.getText(), Color.WHITE, Color.BLACK, 1, 0, 0);
		this.input.draw();
	}
	
	public boolean mouseEvent() {
		if(Mideas.getHover() && Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x+this.x_size && Mideas.mouseY() >= this.y && Mideas.mouseY() <= this.y+Sprites.edit_box_left_border.getImageHeight()*Mideas.getDisplayYFactor()) {
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
				if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
					this.buttonDown = false;
					this.input.setIsActive(true);
					return true;
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
	
	public void setText(String text) {
		this.input.setText(text);
	}
	
	public void resetText() {
		this.input.resetText();
	}
	
	public String getText() {
		return this.input.getText();
	}
	
	public boolean keyboardEvent() {
		return this.input.event();
	}
	
	public void setActive(boolean we) {
		this.input.setIsActive(we);
	}
	
	public int getValue() {
		return this.input.getTextValue();
	}
	
	public boolean checkValue(int value) {return true;}
	
	public void update(float x, float y) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)(this.xSizeSave*Mideas.getDisplayXFactor());
		this.input.update(this.x+this.textOffset*Mideas.getDisplayXFactor(), this.y+3, this.inputMaxWidth*Mideas.getDisplayXFactor(), this.cursorWidth*Mideas.getDisplayXFactor(), this.cursorHeight*Mideas.getDisplayYFactor());
	}
	
	protected boolean keyEvent(@SuppressWarnings("unused") char c) {return false;}
}
