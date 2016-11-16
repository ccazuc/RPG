package com.mideas.rpg.v2.utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF;

public class Button {

	private float x;
	private float y;
	private float x_size = Sprites.button.getImageWidth();
	private float y_size = Sprites.button.getImageHeight();
	private Texture texture = Sprites.button;
	private String text;
	private int textWidth;
	private int shadow_size;
	protected TTF font;
	private boolean buttonDown;
	private boolean buttonHover;
	private Color color = Color.decode("#FFC700");
	private Color hoveredColor = Color.white;
	private Color baseColor = Color.decode("#FFC700");
	private boolean hasClicked;
	private static final Color GREY = Color.decode("#808080");
	private boolean isEnable = true;
	
	public Button(float x, float y, float x_size, float y_size, String text, float font_size, int shadow_size) {
		this.x = x;
		this.y = y;
		this.x_size = x_size;
		this.y_size = y_size;
		this.text = text;
		this.shadow_size = shadow_size;
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

	public Button(float x, float y, float x_size, float y_size, String text, float font_size, int shadow_size, Color baseColor, Color hoveredColor) {
		this.x = x;
		this.y = y;
		this.x_size = x_size;
		this.y_size = y_size;
		this.text = text;
		this.shadow_size = shadow_size;
		this.baseColor = baseColor;
		this.hoveredColor = hoveredColor;
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

	public Button(float x, float y, String text, float font_size) {
		this.x = x;
		this.y = y;
		this.text = text;
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
	
	public void draw() {
		if(this.isEnable) {
			if(!activateCondition()) {
				this.texture = Sprites.button_disabled;
				this.color = GREY;
			}
			else if(!this.buttonDown && !this.buttonHover && !hoverSpriteActivateCondition()) {
				this.texture = Sprites.button;
				this.color = this.baseColor;
			}
			else if(hoverSpriteActivateCondition()) {
				this.color = this.hoveredColor;
			}
			Draw.drawQuad(this.texture, this.x, this.y, this.x_size, this.y_size);
			this.font.drawStringShadow(this.x-this.textWidth/2+this.x_size/2, this.y-this.font.getLineHeight()/2+this.y_size/2, this.text, this.color, Color.black, this.shadow_size, 0, 0);
		}
	}
	
	public boolean event() {
		if(this.isEnable && activateCondition()) {
			this.color = this.baseColor;
			this.buttonHover = false;
			if(Mideas.getHover() && Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x+this.x_size && Mideas.mouseY() >= this.y && Mideas.mouseY() <= this.y+this.y_size) {
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
						this.color = this.hoveredColor;
						this.texture = Sprites.button_hover;
						eventButtonClick();
						this.hasClicked = true;
						return true;
					}
					else if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
						this.buttonDown = false;
					}
				}
				this.color = this.hoveredColor;
			}
			else if(!Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
					this.buttonDown = false;
					this.hasClicked = false;
				}
			}
			if(this.buttonDown) {
				if(this.buttonHover || hoverSpriteActivateCondition()) {
					this.texture = Sprites.button_down_hover;
				}
				else {
					this.texture = Sprites.button_down;
				}
			}
			else if(this.buttonHover || hoverSpriteActivateCondition()) {
				this.texture = Sprites.button_hover;
			}
			else {
				this.texture = Sprites.button;
			}
		}
		else {
			this.texture = Sprites.button_disabled;
			this.color = GREY;
		}
		return false;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setHoverFalse() {
		this.buttonHover = false;
	}
	
	public void setButtonWidth(float width) {
		this.x_size = width;
	}
	
	public void setButtonHeight(float height) {
		this.y_size = height;
	}
	
	public void enable() {
		this.isEnable = true;
	}
	
	public void disable() {
		reset();
		this.isEnable = false;
	}
	
	protected void eventButtonClick() {}
	protected boolean activateCondition() {return true;}
	protected boolean hoverSpriteActivateCondition() {return false;}
	protected void popupClosed() {}
	
	public boolean getButtonDown() {
		return this.buttonDown;
	}
	
	public boolean hasClicked() {
		return this.hasClicked;
	}
	
	public boolean isHover() {
		return this.buttonHover;
	}
	
	public void reset() {
		this.buttonDown = false;
		this.buttonHover = false;
		this.hasClicked = false;
		this.texture = Sprites.button;
		this.color = this.baseColor;
	}
	
	public void update(float x, float y, float x_size, float y_size) {
		this.x = x;
		this.y = y;
		this.x_size = x_size;
		this.y_size = y_size;
	}
}
