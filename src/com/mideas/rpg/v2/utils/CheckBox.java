package com.mideas.rpg.v2.utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.util.ResourceLoader;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF;
import com.mideas.rpg.v2.TTF2;

public class CheckBox {

	private float x;
	private float y;
	private int x_size;
	private int y_size;
	private String text;
	private String tooltip_text;
	private boolean mouseHover;
	private boolean leftButtonDown;
	private boolean rightButtonDown;
	private Color color;
	private TTF font;
	private int textWidth;
	
	public CheckBox(float x, float y, String text, String tooltip_text, Color color, float font_size) {
		this.x = x;
		this.x_size = (int)(Sprites.check_box.getImageWidth()*Mideas.getDisplayXFactor());
		this.y_size = (int)(Sprites.check_box.getImageHeight()*Mideas.getDisplayYFactor());
		this.y = y;
		this.text = text;
		this.tooltip_text = tooltip_text;
		this.color = color;
		InputStream inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
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
		this.textWidth = this.font.getWidth(this.text);
	}
	
	public CheckBox(float x, float y, String text, Color color, float font_size) {
		this.x = x;
		this.y = y;
		this.x_size = (int)(Sprites.check_box.getImageWidth()*Mideas.getDisplayXFactor());
		this.y_size = (int)(Sprites.check_box.getImageHeight()*Mideas.getDisplayYFactor());
		this.text = text;
		this.color = color;
	}
	
	public CheckBox(float x, float y, float x_size, float y_size) {
		this.x = x;
		this.y = y;
		this.x_size = (int)x_size;
		this.y_size = (int)y_size;
	}
	
	public void draw() {
		Draw.drawQuad(Sprites.check_box, this.x, this.y, this.x_size, this.y_size);
		if(this.mouseHover) {
			Draw.drawQuadBlend(Sprites.check_box_hover, this.x+1, this.y+1, this.x_size-2, this.y_size-2);
			if(this.tooltip_text != null) {
				//draw tooltip
			}
		}
		if(this.leftButtonDown || this.rightButtonDown) {
			Draw.drawQuad(Sprites.check_box_down, this.x, this.y, this.x_size, this.y_size);
		}
		if(get()) {
			Draw.drawQuad(Sprites.check_box_enable, this.x-1, this.y-1);
		}
		if(this.text != null) {
			this.font.drawStringShadow(this.x+(Sprites.check_box.getImageWidth()+10)*Mideas.getDisplayXFactor(), this.y, this.text, this.color, Color.black, 1, 0, 0);
		}
	}
	
	public boolean event() {
		if(Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x+(Sprites.check_box.getImageWidth()+10)*Mideas.getDisplayXFactor()+this.textWidth && Mideas.mouseY() >= this.y-3 && Mideas.mouseY() <= this.y+3+Sprites.check_box.getImageHeight()*Mideas.getDisplayYFactor()) {
			this.mouseHover = true;
			if(Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0) {
					this.leftButtonDown = true;
				}
				else if(Mouse.getEventButton() == 1) {
					this.rightButtonDown = true;
				}
			}
			else {
				if(Mouse.getEventButton() == 0) {
					set();
					this.leftButtonDown = false;
					this.rightButtonDown = false;
					return true;
				}
				else if(Mouse.getEventButton() == 1) {
					this.rightButtonDown = false;
					this.leftButtonDown = false;
					return true;
				}
			}
		}
		else {
			if(!Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
					this.rightButtonDown = false;
					this.leftButtonDown = false;
				}
			}
			this.mouseHover = false;
		}
		return false;
	}
	
	public void update(float x, float y) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)(Sprites.check_box.getImageWidth()*Mideas.getDisplayXFactor());
		this.y_size = (int)(Sprites.check_box.getImageHeight()*Mideas.getDisplayYFactor());
	}
	
	public void update(float x, float y, float x_size, float y_size) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)(x_size);
		this.y_size = (int)(y_size);
	}
	
	protected void set() {}
	protected boolean get() {return false;}
}
