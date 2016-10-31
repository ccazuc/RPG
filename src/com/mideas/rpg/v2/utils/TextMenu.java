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
	
	public TextMenu(float x, float y, float x_size, float y_size, String text, int font_size, int shadow_size) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)x_size;
		this.text = text;
		this.shadow_size = shadow_size;
		InputStream inputStream = ResourceLoader.getResourceAsStream("sprite/police/ARIALN.TTF");
		Font awtFont = null;
		try {
			awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont((float)font_size);
		} 
		catch (FontFormatException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		this.font = new TTF(awtFont, true);
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
			this.font.drawStringShadow(x, y, this.text, GRAY, Color.black, this.shadow_size, 0, 0);
		}
		else {
			if(this.buttonHover) {
				Draw.drawQuad(Sprites.text_menu_hover, x-10*Mideas.getDisplayXFactor(), y+3*Mideas.getDisplayYFactor());
			}
			if(this.buttonDown) {
				this.font.drawStringShadow(x+2, y+2, this.text, Color.white, Color.black, this.shadow_size, 0, 0);
			}
			else {
				this.font.drawStringShadow(x, y, this.text, Color.white, Color.black, this.shadow_size, 0, 0);
			}
		}
	}
	
	public void eventX(float x) {
		eventHandler((int)x, this.y);
	}
	
	public void eventY(float y) {
		eventHandler(this.x, (int)y);
	}
	
	public void eventXY(float x, float y) {
		eventHandler((int)x, (int)y);
	}
	
	public void event() {
		eventHandler(this.x, this.y);
	}
	
	private boolean eventHandler(int x, int y) {
		this.buttonHover = false;
		if(Mideas.mouseX() >= x && Mideas.mouseX() <= x+this.x_size && Mideas.mouseY() > y+2 && Mideas.mouseY() <= y+this.font.getLineHeight()+2) {
			this.buttonHover = true;
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
		return false;
	}
	
	public void reset() {
		this.buttonDown = false;
		this.buttonHover = false;
	}
	
	public boolean activateCondition() { return true; }
	public void eventButtonClick() {}
}
