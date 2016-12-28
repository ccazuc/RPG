package com.mideas.rpg.v2.utils;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF;
import com.mideas.rpg.v2.FontManager;

public class TextMenu {

	private int x;
	private int y;
	private String text;
	private TTF font;
	private int shadow_size;
	private boolean buttonHover;
	private boolean buttonDown;
	private int x_size;
	protected int value;
	private int textShift;
	private boolean isActive;
	
	public TextMenu(float x, float y, float x_size, String text, float font_size, int shadow_size, float textShift) {
		this.font = FontManager.get("FRIZQT", font_size);
		this.textShift = (int)textShift;
		this.shadow_size = shadow_size;
		this.x_size = (int)x_size;
		this.text = text;
		this.x = (int)x;
		this.y = (int)y;
	}
	
	public TextMenu(float x, float y, float x_size, String text, float font_size, int shadow_size) {
		this.font = FontManager.get("FRIZQT", font_size);
		this.shadow_size = shadow_size;
		this.x_size = (int)x_size;
		this.text = text;
		this.x = (int)x;
		this.y = (int)y;
	}
	
	public TextMenu(TextMenu menu) {
		this.shadow_size = menu.shadow_size;
		this.x_size = menu.x_size;
		this.value = menu.value;
		this.text = menu.text;
		this.font = menu.font;
		this.x = menu.x;
		this.y = menu.y;
	}
	
	public void drawX(float x) {
		draw(x, this.y);
	}
	
	public void drawY(float y) {
		draw(this.x, y);
	}
	
	public void drawXY(float x, float y) {
		draw(x, y);
	}
	
	public void draw() {
		draw(this.x, this.y);
	}
	
	private void draw(float x, float y) {
		if(!activateCondition()) {
			this.font.drawStringShadow(x+this.textShift, y, this.text, Color.GREY, Color.BLACK, this.shadow_size, 0, 0);
			return;
		}
		if(this.buttonDown) {
			this.font.drawStringShadow(x+2+this.textShift, y+2, this.text, Color.WHITE, Color.BLACK, this.shadow_size, 0, 0);
		}
		else {
			this.font.drawStringShadow(x+this.textShift, y, this.text, Color.WHITE, Color.BLACK, this.shadow_size, 0, 0);
		}
		if(this.buttonHover) {
			Draw.drawQuadBlend(Sprites.text_menu_hover, x, y+2, this.x_size, Sprites.text_menu_hover.getImageHeight()*Mideas.getDisplayYFactor());
		}
	}
	
	public boolean eventX(float x) {
		return eventHandler((int)x, this.y);
	}
	
	public boolean eventY(float y) {
		return eventHandler(this.x, (int)y);
	}
	
	public boolean eventXY(float x, float y) {
		return eventHandler((int)x, (int)y);
	}
	
	public boolean event() {
		return eventHandler(this.x, this.y);
	}
	
	private boolean eventHandler(int x, int y) {
		if(Mideas.getHover() && Mideas.mouseX() >= x && Mideas.mouseX() <= x+this.x_size && Mideas.mouseY() >= y && Mideas.mouseY() <= y+this.font.getLineHeight()+1) {
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
		return false;
	}
	
	public void reset() {
		this.buttonDown = false;
		this.buttonHover = false;
	}
	
	public String getText() {
		return this.text;
	}
	
	public TTF getFont() {
		return this.font;
	}
	
	public void setY(float y) {
		this.y = (int)y;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setX(float x) {
		this.x = (int)x;
	}
	
	public int getX() {
		return this.x;
	}
	
	public void setWidth(float x_size) {
		this.x_size = (int)x_size;
	}
	
	public void setActive(boolean we) {
		this.isActive = we;
	}
	
	public void setTextShift(float shift) {
		this.textShift = (int)shift;
	}
	
	public int getTextShift() {
		return this.textShift;
	}
	
	public void update(float x, float y, float x_size, float textShift) {
		this.x = (int)x;
		this.y = (int)y;
		this.textShift = (int)textShift;
		this.x_size = (int)x_size;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public boolean activateCondition() { return true; }
	public void eventButtonClick() {}
}
