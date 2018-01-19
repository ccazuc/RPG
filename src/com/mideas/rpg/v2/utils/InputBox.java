package com.mideas.rpg.v2.utils;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.utils.render.Draw;
import com.mideas.rpg.v2.utils.render.Sprites;
import com.mideas.rpg.v2.utils.render.TTF;

import org.lwjgl.input.Mouse;

public class InputBox {

	private final short textOffset;
	private final short inputMaxWidth;
	private final short xSizeSave;
	protected final Input input;
	private short x;
	private short y;
	private short x_size;
	private boolean buttonDown;
	private boolean buttonHover;
	private final EditBoxCrossButton crossButton;
	private final static byte CROSS_BUTTON_X_OFFSET = 18;
	private final static byte CROSS_BUTTON_Y_OFFSET = 4;
	
	public InputBox(float x, float y, float x_size, int inputMaxLength, float textOffset, float inputMaxWidth, TTF font, boolean isIntegerInput, int cursorWidth, int cursorHeight, String defaultText, boolean hasCrossButton) {
		this.x = (short)x;
		this.y = (short)y;
		this.xSizeSave = (short)x_size;
		this.x_size = (short)(x_size*Mideas.getDisplayXFactor());
		this.textOffset = (short)textOffset;
		this.inputMaxWidth = (short)inputMaxWidth;
		if(isIntegerInput) {
			this.input = new IntegerInput(font, x+textOffset*Mideas.getDisplayXFactor(), y+3, inputMaxLength, inputMaxWidth*Mideas.getDisplayXFactor(), cursorWidth, cursorHeight) {
				
				@Override
				public boolean checkValue(int value) {
					return InputBox.this.checkValue(value);
				}
				
				@Override
				public boolean keyEvent(char c) {
					return InputBox.this.keyEvent(c);
				}
			};
		}
		else {
			this.input = new Input(font, inputMaxLength, false, x+textOffset*Mideas.getDisplayXFactor(), y+3, inputMaxWidth*Mideas.getDisplayXFactor(), cursorWidth, cursorHeight, defaultText) {
				
				@Override
				public boolean keyEvent(char c) {
					return InputBox.this.keyEvent(c);
				}
			};
		}
		if(hasCrossButton) {
			this.crossButton = new EditBoxCrossButton(x+x_size-CROSS_BUTTON_X_OFFSET*Mideas.getDisplayXFactor(), y+CROSS_BUTTON_Y_OFFSET*Mideas.getDisplayYFactor()) {
				
				@Override
				public void eventButtonClick() {
					setActive(false);
					resetText();
				}
				
				@Override
				public boolean activateCondition() {
					return InputBox.this.input.isActive() || InputBox.this.input.getText().length() > 0;
				}
			};
		}
		else {
			this.crossButton = null;
		}
	}
	
	public InputBox(float x, float y, float x_size, int inputMaxLength, float textOffset, float inputMaxWidth, TTF font, boolean isIntegerInput) {
		this(x, y, x_size, inputMaxLength, textOffset, inputMaxWidth, font, isIntegerInput, 2, 13, "", false);
	}
	
	public void drawTexturePart() {
		int imageWidth = Sprites.edit_box_left_border.getImageWidth();
		int imageHeight = (int)(Sprites.edit_box_left_border.getImageHeight()*Mideas.getDisplayYFactor());
		Draw.drawQuadPart(Sprites.edit_box, this.x, this.y, imageWidth, imageHeight, 0, 0, 6, 20);				//left_border
		Draw.drawQuadPart(Sprites.edit_box, this.x+imageWidth, this.y, this.x_size-2*imageWidth, imageHeight, 8, 0, 1, 20);	//middle_border
		Draw.drawQuadPart(Sprites.edit_box, this.x+this.x_size-imageWidth, this.y, imageWidth, imageHeight, 11, 0, 6, 20);	//right_border
	}
	
	public void drawString() {
		this.input.draw();
		if(this.crossButton != null) {
			this.crossButton.draw();
		}
	}
	
	public void draw() {
		int imageWidth = Sprites.edit_box_left_border.getImageWidth();
		int imageHeight = (int)(Sprites.edit_box_left_border.getImageHeight()*Mideas.getDisplayYFactor());
		/*Draw.drawQuad(Sprites.edit_box_left_border, this.x, this.y, imageWidth, imageHeight);
		Draw.drawQuad(Sprites.edit_box_middle_border, this.x+imageWidth, this.y, this.x_size-2*imageWidth, imageHeight);
		Draw.drawQuad(Sprites.edit_box_right_border, this.x+this.x_size-imageWidth, this.y, imageWidth, imageHeight);*/
		
		Sprites.edit_box.drawBegin();
		Draw.drawQuadPart(Sprites.edit_box, this.x, this.y, imageWidth, imageHeight, 0, 0, 6, 20);				//left_border
		Draw.drawQuadPart(Sprites.edit_box, this.x+imageWidth, this.y, this.x_size-2*imageWidth, imageHeight, 8, 0, 1, 20);	//middle_border
		Draw.drawQuadPart(Sprites.edit_box, this.x+this.x_size-imageWidth, this.y, imageWidth, imageHeight, 11, 0, 6, 20);	//right_border
		Sprites.edit_box.drawEnd();
		this.input.draw();
		if(this.crossButton != null) {
			this.crossButton.draw();
		}
	}
	
	public boolean mouseEvent() {
		if(this.crossButton != null && this.crossButton.event()) {
			return true;
		}
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
	
	public void setCursorEndOfText()
	{
		this.input.setText(this.input.getText());
	}
	
	@SuppressWarnings("unused")
	public boolean checkValue(int value) {return true;}
	
	public void update(float x, float y) {
		this.x = (short)x;
		this.y = (short)y;
		this.x_size = (short)(this.xSizeSave*Mideas.getDisplayXFactor());
		this.input.update(this.x+this.textOffset*Mideas.getDisplayXFactor(), this.y+3, this.inputMaxWidth*Mideas.getDisplayXFactor());
		if(this.crossButton != null) {
			this.crossButton.update(this.x+this.x_size-CROSS_BUTTON_X_OFFSET*Mideas.getDisplayXFactor(), y+CROSS_BUTTON_Y_OFFSET*Mideas.getDisplayYFactor());
		}
	}
	
	public short getWidth() {
		return this.x_size;
	}
	
	protected boolean keyEvent(@SuppressWarnings("unused") char c) {return false;}
}
