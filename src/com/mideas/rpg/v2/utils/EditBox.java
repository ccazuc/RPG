package com.mideas.rpg.v2.utils;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF;

import org.lwjgl.input.Mouse;

public class EditBox {

	protected Input input;
	private int x;
	private int y;
	private int x_size;
	private TTF font;
	private boolean buttonDown;
	private boolean buttonHover;
	private Color bgColor;
	
	public EditBox(float x, float y, float x_size, int maxLength, boolean debugActive, TTF font, float opacity) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)x_size;
		this.input = new Input(font, maxLength, false, false) {
			
			@Override
			public boolean keyEvent(char c) {
				return EditBox.this.keyEvent(c);
			}
		};
		this.font = font;
		this.bgColor = new Color(0, 0, 0, opacity);
	}
	
	public void draw() {
		int imageWidth = Sprites.edit_box_left_border.getImageWidth();
		int imageHeight = (int)(Sprites.edit_box_left_border.getImageHeight()*Mideas.getDisplayYFactor());
		Draw.drawColorQuad(this.x, this.y, this.x_size, imageHeight, this.bgColor);
		Draw.drawQuad(Sprites.edit_box_left_border, this.x, this.y, imageWidth, imageHeight);
		Draw.drawQuad(Sprites.edit_box_middle_border, this.x+imageWidth, this.y, this.x_size-2*imageWidth, imageHeight);
		Draw.drawQuad(Sprites.edit_box_right_border, this.x+this.x_size-imageWidth, this.y, imageWidth, imageHeight);
		this.font.drawStringShadow(this.x+6, this.y+2, this.input.getText(), Color.WHITE, Color.BLACK, 1, 0, 0);
		if(this.input.isActive() && System.currentTimeMillis()%1000 < 500) {
			Draw.drawColorQuad(this.x+this.font.getWidth(this.input.getText())+6, this.y+3, 4*Mideas.getDisplayXFactor(), 15*Mideas.getDisplayYFactor(), Color.WHITE);
		}
	}
	
	public boolean mouseEvent() {
		this.buttonHover = false;
		if(Mideas.getHover() && Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x+this.x_size && Mideas.mouseY() >= this.y && Mideas.mouseY() <= this.y+Sprites.edit_box_left_border.getImageHeight()*Mideas.getDisplayYFactor()) {
			this.buttonHover = true;
			Mideas.setHover(false);
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
	
	public String getText() {
		return this.input.getText();
	}
	
	public boolean keyboardEvent() {
		return this.input.event();
	}
	
	public void setActive(boolean we) {
		this.input.setIsActive(we);
	}
	
	public void update(float x, float y, float x_size) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)x_size;
	}
	
	protected boolean keyEvent(char c) {return false;};
}
