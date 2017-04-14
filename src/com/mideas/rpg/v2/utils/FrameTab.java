package com.mideas.rpg.v2.utils;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.utils.render.Draw;
import com.mideas.rpg.v2.utils.render.Sprites;
import com.mideas.rpg.v2.utils.render.TTF;
import com.mideas.rpg.v2.FontManager;

public class FrameTab {

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
	
	public FrameTab(float x, float y, float x_size, float y_size, String text, float font_size, int shadow_size, boolean isSelected) {
		this.font = FontManager.get("FRIZQT", font_size);
		this.textWidth = this.font.getWidth(text);
		this.shadow_size = shadow_size;
		this.isSelected = isSelected;
		this.x_size = (int)x_size;
		this.y_size = (int)y_size;
		this.text = text;
		this.x = (int)x;
		this.y = (int)y;
	}
	
	public void draw() {
		if(this.isSelected) {
			int sideWidth = Sprites.frame_tab_active_left_part.getImageWidth();
			int x = (int)(this.x+1*Mideas.getDisplayXFactor());
			int y = (int)(this.y+3*Mideas.getDisplayYFactor());
			int x_size = (int)(this.x_size+2*Mideas.getDisplayYFactor());
			int y_size = (int)(this.y_size-3*Mideas.getDisplayYFactor());
			Draw.drawQuad(Sprites.frame_tab_active_left_part, x, y, sideWidth, y_size);
			Draw.drawQuad(Sprites.frame_tab_active_middle_part, x+sideWidth, y, x_size-2*sideWidth, y_size);
			Draw.drawQuad(Sprites.frame_tab_active_right_part, x+x_size-sideWidth, y, sideWidth, y_size);
			this.font.drawStringShadow(x+x_size/2-this.textWidth/2, y+y_size/2-this.font.getLineHeight()/2, this.text, this.selectedColor, Color.BLACK, this.shadow_size, 0, 0);
		}
		else {
			int sideWidth = Sprites.frame_tab_inactive_left_part.getImageWidth();
			int y = (int)(this.y+7*Mideas.getDisplayYFactor());
			int y_size = (int)(this.y_size-9*Mideas.getDisplayYFactor());
			Draw.drawQuad(Sprites.frame_tab_inactive_left_part, this.x, y, sideWidth, y_size);
			Draw.drawQuad(Sprites.frame_tab_inactive_middle_part, this.x+sideWidth, y, this.x_size-2*sideWidth, y_size);
			Draw.drawQuad(Sprites.frame_tab_inactive_right_part, this.x+this.x_size-sideWidth, y, sideWidth, y_size);
			if(activateCondition()) {
				if(this.buttonDown) {
					this.font.drawStringShadow(this.x+this.x_size/2-this.textWidth/2+2, y+y_size/2-this.font.getLineHeight()/2+2, this.text, this.color, Color.BLACK, this.shadow_size, 0, 0);
				}
				else {
					this.font.drawStringShadow(this.x+this.x_size/2-this.textWidth/2, y+y_size/2-this.font.getLineHeight()/2, this.text, this.color, Color.BLACK, this.shadow_size, 0, 0);
				}
				if(this.buttonHover) {
					Draw.drawQuadBlend(Sprites.button_menu_hover, this.x, y, this.x_size, y_size+7*Mideas.getDisplayYFactor());
				}
			}
			else {
				this.font.drawStringShadow(this.x+this.x_size/2-this.textWidth/2, y+y_size/2-this.font.getLineHeight()/2, this.text, Color.GREY, Color.BLACK, this.shadow_size, 0, 0);
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
