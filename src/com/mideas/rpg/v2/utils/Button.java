package com.mideas.rpg.v2.utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

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
	private TTF font;
	private boolean buttonDown;
	private boolean buttonHover;
	private Color color = Color.decode("#FFC700");
	private boolean hasClicked;
	
	@SuppressWarnings("null")
	public Button(float x, float y, float x_size, float y_size, String text, float size) {
		this.x = x;
		this.y = y;
		this.x_size = x_size;
		this.y_size = y_size;
		this.text = text;
		InputStream inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		Font awtFont = null;
		try {
			try {
				awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			} 
			catch (FontFormatException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		awtFont = awtFont.deriveFont(size).deriveFont(Font.BOLD);
		this.font = new TTF(awtFont, true);
	}
	
	@SuppressWarnings("null")
	public Button(int x, int y, String text) {
		this.x = x;
		this.y = y;
		this.text = text;
		InputStream inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		Font awtFont = null;
		try {
			try {
				awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			}
			catch (FontFormatException e) {
				e.printStackTrace();
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		awtFont = awtFont.deriveFont(15f).deriveFont(Font.BOLD);
		this.font = new TTF(awtFont, true);
	}

	@SuppressWarnings("null")
	public Button(float x, float y, String text, float size) {
		this.x = x;
		this.y = y;
		this.text = text;
		InputStream inputStream = ResourceLoader.getResourceAsStream("sprite/police/FRIZQT__.TTF");
		Font awtFont = null;
		try {
			try {
				awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			} 
			catch (FontFormatException e) {
				e.printStackTrace();
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		awtFont = awtFont.deriveFont(size).deriveFont(Font.BOLD);
		this.font = new TTF(awtFont, true);
	}
	
	public void draw() {
		Draw.drawQuad(this.texture, this.x, this.y, this.x_size, this.y_size);
		this.font.drawStringShadow(this.x-this.font.getWidth(this.text)/2+this.x_size*Mideas.getDisplayXFactor()/2, this.y+-this.font.getLineHeight()/2+this.y_size*Mideas.getDisplayYFactor()/2, this.text, this.color, Color.black, 1, 1);
	}
	
	public void event() throws SQLException, NoSuchAlgorithmException {
		this.color = Color.decode("#FFC700");
		this.buttonHover = false;
		if(Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x+this.x_size && Mideas.mouseY() >= this.y && Mideas.mouseY() <= this.y+this.y_size) {
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
					this.buttonHover = false;
					this.color = Color.decode("#FFC700");
					this.texture = Sprites.button;
					eventButtonClick();
					this.hasClicked = true;
					return;
				}
				else if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
					this.buttonDown = false;
				}
			}
			this.color = Color.white;
		}
		else if(!Mouse.getEventButtonState()) {
			if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
				this.buttonDown = false;
				this.hasClicked = false;
			}
		}
		if(this.buttonDown) {
			if(this.buttonHover) {
				this.texture = Sprites.button_down_hover;
			}
			else {
				this.texture = Sprites.button_down;
			}
		}
		else if(this.buttonHover) {
			this.texture = Sprites.button_hover;
		}
		else {
			this.texture = Sprites.button;
		}
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setButtonWidth(float width) {
		this.x_size = width;
	}
	
	public void setButtonHeight(float height) {
		this.y_size = height;
	}
	
	@SuppressWarnings({ "unused"})
	public void eventButtonClick() throws SQLException, NoSuchAlgorithmException {}
	
	public boolean getButtonDown() {
		return this.buttonDown;
	}
	
	public boolean hasClicked() {
		return this.hasClicked;
	}
	
	public void reset() {
		this.buttonDown = false;
		this.buttonHover = false;
		this.texture = Sprites.button;
		this.color = Color.decode("#FFC700");
	}
	
	public void update(float x, float y, float x_size, float y_size) {
		this.x = x;
		this.y = y;
		this.x_size = x_size;
		this.y_size = y_size;
	}
}