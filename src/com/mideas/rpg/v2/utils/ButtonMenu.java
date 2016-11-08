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

public class ButtonMenu {

	private int x;
	private int y;
	private int x_size;
	private int y_size;
	private String text;
	private boolean isSelected;
	private boolean buttonHover;
	private boolean buttonDown;
	private Color defaultColor = Color.decode("#FFC700");
	private Color hoveredColor = Color.white;
	private Color selectedColor = Color.white;
	private Color color = this.defaultColor;
	private int shadow_size;
	private TTF font;
	private static final Color GREY = Color.decode("#808080");

	public ButtonMenu(float x, float y, float x_size, float y_size, String text, float font_size, int shadow_size, boolean isSelected) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)x_size;
		this.y_size = (int)y_size;
		this.text = text;
		this.isSelected = isSelected;
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
	}
	
	public void draw() {
		if(this.isSelected) {
			int sideWidth = (int)(Sprites.button_menu_selected_left.getImageWidth()*Mideas.getDisplayXFactor());
			Draw.drawQuad(Sprites.button_menu_selected_left, this.x, this.y, sideWidth, this.y_size);
			Draw.drawQuad(Sprites.button_menu_selected_middle, this.x+sideWidth, this.y, this.x_size-2*sideWidth, this.y_size);
			Draw.drawQuad(Sprites.button_menu_selected_right, this.x+this.x_size-sideWidth, this.y, sideWidth, this.y_size);
			this.font.drawStringShadow(this.x+this.x_size/2-this.font.getWidth(this.text)/2, this.y+this.y_size/2-this.font.getLineHeight()/2, this.text, this.selectedColor, Color.black, this.shadow_size, 0, 0);
		}
		else {
			//Draw.drawQuad(Sprites.button_menu, this.x, this.y+this.anchor_difference, this.x_size, this.y_size+Math.abs(this.anchor_difference));
			int sideWidth = (int)(Sprites.button_menu_unselected_left.getImageWidth()*Mideas.getDisplayXFactor());
			Draw.drawQuad(Sprites.button_menu_unselected_left, this.x, this.y, sideWidth, this.y_size);
			Draw.drawQuad(Sprites.button_menu_unselected_middle, this.x+sideWidth, this.y, this.x_size-2*sideWidth, this.y_size);
			Draw.drawQuad(Sprites.button_menu_unselected_right, this.x+this.x_size-sideWidth, this.y, sideWidth, this.y_size);
			Draw.drawQuad(Sprites.button_menu_unselected_top, this.x, this.y+1, this.x_size, Sprites.button_menu_unselected_top.getImageHeight()*Mideas.getDisplayYFactor());
			if(activateCondition()) {
				if(this.buttonDown) {
					this.font.drawStringShadow(this.x+this.x_size/2-this.font.getWidth(this.text)/2+2, this.y+this.y_size/2-this.font.getLineHeight()/2+2, this.text, this.color, Color.black, this.shadow_size, 0, 0);
				}
				else {
					this.font.drawStringShadow(this.x+this.x_size/2-this.font.getWidth(this.text)/2, this.y+this.y_size/2-this.font.getLineHeight()/2, this.text, this.color, Color.black, this.shadow_size, 0, 0);
				}
				if(this.buttonHover) {
					Draw.drawQuadBlend(Sprites.button_menu_hover, this.x, this.y, this.x_size, this.y_size);
				}
			}
			else {
				this.font.drawStringShadow(this.x+this.x_size/2-this.font.getWidth(this.text)/2, this.y+this.y_size/2-this.font.getLineHeight()/2, this.text, GREY, Color.black, this.shadow_size, 0, 0);
			}
		}
	}
	
	public boolean event() {
		if(!this.isSelected && activateCondition()) {
			this.buttonHover = false;
			if(Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x+this.x_size && Mideas.mouseY() >= this.y && Mideas.mouseY() <= this.y+this.y_size) {
				this.buttonHover = true;
				this.color = this.hoveredColor;
			}
			else {
				this.color = this.defaultColor;
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
		}
		return false;
	}
	
	public void update(float x, float y, float x_size, float y_size) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)x_size;
		this.y_size = (int)y_size;
	}
	
	public void update(float x, float y) {
		this.x = (int)x;
		this.y = (int)y;
	}
	
	public boolean isSelected() {
		return this.isSelected;
	}
	
	public void eventButtonClick() {}
	
	public boolean activateCondition() {return true;}
	
	public void setIsSelected(boolean we) {
		this.isSelected = we;
		if(!this.isSelected) {
			this.color = this.defaultColor;
		}
	}
}
