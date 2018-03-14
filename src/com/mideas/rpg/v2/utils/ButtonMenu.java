package com.mideas.rpg.v2.utils;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.render.Draw;
import com.mideas.rpg.v2.render.Sprites;
import com.mideas.rpg.v2.render.TTF;
import com.mideas.rpg.v2.FontManager;

public class ButtonMenu {

	private int x;
	private int y;
	private int x_size;
	private int y_size;
	private String text;
	private int textWidth;
	private boolean isSelected;
	private boolean buttonHover;
	private boolean buttonDown;
	private Color defaultColor = Color.YELLOW;
	private Color hoveredColor = Color.WHITE;
	private Color selectedColor = Color.WHITE;
	private Color color = this.defaultColor;
	private int shadow_size;
	private TTF font;

	public ButtonMenu(float x, float y, float x_size, float y_size, String text, float font_size, int shadow_size, boolean isSelected) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)x_size;
		this.y_size = (int)y_size;
		this.text = text;
		this.isSelected = isSelected;
		this.shadow_size = shadow_size;
		this.font = FontManager.get("FRIZQT", font_size);
		this.textWidth = this.font.getWidth(this.text);
	}
	
	public void draw() {
		if(this.isSelected) {
			int sideWidth = Sprites.button_menu_selected_left.getImageWidth();
			Draw.drawQuad(Sprites.button_menu_selected_left, this.x, this.y, sideWidth, this.y_size);
			Draw.drawQuad(Sprites.button_menu_selected_middle, this.x+sideWidth, this.y, this.x_size-2*sideWidth, this.y_size);
			Draw.drawQuad(Sprites.button_menu_selected_right, this.x+this.x_size-sideWidth, this.y, sideWidth, this.y_size);
			this.font.drawStringShadow(this.x+this.x_size/2-this.textWidth/2, this.y+this.y_size/2-this.font.getLineHeight()/2, this.text, this.selectedColor, Color.BLACK, this.shadow_size, 0, 0);
		}
		else {
			int sideWidth = Sprites.button_menu_unselected_left.getImageWidth();
			Draw.drawQuad(Sprites.button_menu_unselected_left, this.x, this.y, sideWidth, this.y_size);
			Draw.drawQuad(Sprites.button_menu_unselected_middle, this.x+sideWidth, this.y, this.x_size-2*sideWidth, this.y_size);
			Draw.drawQuad(Sprites.button_menu_unselected_right, this.x+this.x_size-sideWidth, this.y, sideWidth, this.y_size);
			Draw.drawQuad(Sprites.button_menu_unselected_top, this.x, this.y+1, this.x_size, Sprites.button_menu_unselected_top.getImageHeight()*Mideas.getDisplayYFactor());
			if(activateCondition()) {
				if(this.buttonDown) {
					this.font.drawStringShadow(this.x+this.x_size/2-this.textWidth/2+2, this.y+this.y_size/2-this.font.getLineHeight()/2+2, this.text, this.color, Color.BLACK, this.shadow_size, 0, 0);
				}
				else {
					this.font.drawStringShadow(this.x+this.x_size/2-this.textWidth/2, this.y+this.y_size/2-this.font.getLineHeight()/2, this.text, this.color, Color.BLACK, this.shadow_size, 0, 0);
				}
				if(this.buttonHover) {
					Draw.drawQuadBlend(Sprites.button_menu_hover, this.x, this.y, this.x_size, this.y_size);
				}
			}
			else {
				this.font.drawStringShadow(this.x+this.x_size/2-this.textWidth/2, this.y+this.y_size/2-this.font.getLineHeight()/2, this.text, Color.GREY, Color.BLACK, this.shadow_size, 0, 0);
			}
		}
	}
	
	public boolean event() {
		if(!(!this.isSelected && activateCondition())) {
			return false;
		}
		if(Mideas.getHover() && Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x+this.x_size && Mideas.mouseY() >= this.y+4 && Mideas.mouseY() <= this.y+this.y_size) {
			this.buttonHover = true;
			this.color = this.hoveredColor;
			Mideas.setHover(false);
		}
		else {
			this.buttonHover = false;
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
