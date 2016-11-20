package com.mideas.rpg.v2.utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.util.ResourceLoader;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF;

public class TextMenu {

	private final static Color GRAY = Color.decode("#47494D");
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
	
	public TextMenu(float x, float y, float x_size, float y_size, String text, float font_size, int shadow_size, float textShift) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)x_size;
		this.text = text;
		this.shadow_size = shadow_size;
		InputStream inputStream = ResourceLoader.getResourceAsStream("sprite/police/ARIALN.TTF");
		Font awtFont = null;
		try {
			awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(font_size);
		} 
		catch (FontFormatException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		this.font = new TTF(awtFont, true);
		this.textShift = (int)textShift;
	}
	
	public TextMenu(float x, float y, float x_size, float y_size, String text, float font_size, int shadow_size) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)x_size;
		this.text = text;
		this.shadow_size = shadow_size;
		InputStream inputStream = ResourceLoader.getResourceAsStream("sprite/police/ARIALN.TTF");
		Font awtFont = null;
		try {
			awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(font_size);
		} 
		catch (FontFormatException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		this.font = new TTF(awtFont, true);
	}
	
	public TextMenu(TextMenu menu) {
		this.x = menu.x;
		this.y = menu.y;
		this.x_size = menu.x_size;
		this.text = menu.text;
		this.shadow_size = menu.shadow_size;
		this.font = menu.font;
		this.value = menu.value;
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
		if(this.isActive) {
			if(!activateCondition()) {
				this.font.drawStringShadow(x+this.textShift, y, this.text, GRAY, Color.black, this.shadow_size, 0, 0);
			}
			else {
				if(this.buttonDown) {
					this.font.drawStringShadow(x+2+this.textShift, y+2, this.text, Color.white, Color.black, this.shadow_size, 0, 0);
				}
				else {
					this.font.drawStringShadow(x+this.textShift, y, this.text, Color.white, Color.black, this.shadow_size, 0, 0);
				}
				if(this.buttonHover) {
					Draw.drawQuadBlend(Sprites.text_menu_hover, x, y+2, this.x_size, Sprites.text_menu_hover.getImageHeight()*Mideas.getDisplayYFactor());
				}
			}
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
		if(this.isActive) {
			this.buttonHover = false;
			if(Mideas.getHover() && Mideas.mouseX() >= x && Mideas.mouseX() <= x+this.x_size && Mideas.mouseY() >= y && Mideas.mouseY() <= y+this.font.getLineHeight()+1) {
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
					if(Mouse.getEventButton() == 0) {
						this.buttonDown = false;
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
	
	public void setX(float x) {
		this.x = (int)x;
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
