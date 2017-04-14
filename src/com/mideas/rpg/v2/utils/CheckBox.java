package com.mideas.rpg.v2.utils;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.utils.render.Draw;
import com.mideas.rpg.v2.utils.render.Sprites;
import com.mideas.rpg.v2.utils.render.TTF;
import com.mideas.rpg.v2.FontManager;

public class CheckBox {

	private float x;
	private float y;
	private int x_size;
	private int y_size;
	private String text;
	private String tooltip_text;
	private boolean buttonHover;
	private boolean buttonDown;
	private Color color;
	private TTF font;
	private int textWidth;
	private boolean state;
	
	public CheckBox(float x, float y, String text, String tooltip_text, Color color, float font_size) {
		this.x = x;
		this.x_size = (int)(Sprites.check_box.getImageWidth()*Mideas.getDisplayXFactor());
		this.y_size = (int)(Sprites.check_box.getImageHeight()*Mideas.getDisplayYFactor());
		this.y = y;
		this.text = text;
		this.tooltip_text = tooltip_text;
		this.color = color;
		this.font = FontManager.get("FRIZQT", font_size);
		this.textWidth = this.font.getWidth(this.text);
	}
	
	public CheckBox(float x, float y, float x_size, float y_size, String text, Color color, float font_size) {
		this.x = x;
		this.y = y;
		this.x_size = (int)(x_size);
		this.y_size = (int)(y_size);
		this.text = text;
		this.color = color;
		this.font = FontManager.get("FRIZQT", font_size);
		this.textWidth = this.font.getWidth(this.text);
	}
	
	public CheckBox(float x, float y, float x_size, float y_size) {
		this.x = x;
		this.y = y;
		this.x_size = (int)x_size;
		this.y_size = (int)y_size;
	}
	
	public CheckBox(float x, float y) {
		this.x = x;
		this.y = y;
		this.x_size = (int)(Sprites.check_box.getImageWidth()*Mideas.getDisplayXFactor());
		this.y_size = (int)(Sprites.check_box.getImageHeight()*Mideas.getDisplayYFactor());
	}
	
	public void draw() {
		Draw.drawQuad(Sprites.check_box, this.x, this.y, this.x_size, this.y_size);
		if(activateCondition()) {
			if(this.buttonHover) {
				Draw.drawQuadBlend(Sprites.check_box_hover, this.x+1, this.y+1, this.x_size-2, this.y_size-2);
				if(this.tooltip_text != null) {
					//draw tooltip
				}
			}
			if(this.buttonDown) {
				Draw.drawQuad(Sprites.check_box_down, this.x, this.y, this.x_size, this.y_size);
			}
			if(get()) {
				Draw.drawQuad(Sprites.check_box_enable, this.x-1, this.y-1, this.x_size, this.y_size);
			}
			if(this.text != null) {
				this.font.drawStringShadow(this.x+(this.x_size+6)*Mideas.getDisplayXFactor(), this.y-1, this.text, this.color, Color.BLACK, 1, 0, 0);
			}
		}
		else {
			if(get()) {
				Draw.drawQuad(Sprites.check_box_disable, this.x-1, this.y-1, this.x_size, this.y_size);
			}
			if(this.text != null) {
				this.font.drawStringShadow(this.x+(this.x_size+6)*Mideas.getDisplayXFactor(), this.y-1, this.text, Color.GREY, Color.BLACK, 1, 0, 0);
			}
		}
	}
	
	public boolean event() {
		if(activateCondition()) {
			this.buttonHover = false;
			if(Mideas.getHover() && Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x+(this.x_size+6)*Mideas.getDisplayXFactor()+this.textWidth && Mideas.mouseY() > this.y-4 && Mideas.mouseY() <= this.y+4+this.y_size) {
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
						set();
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
	
	protected void set() {
		this.state = !this.state;
	}
	
	protected boolean get() {
		return this.state;
	}
	protected boolean activateCondition() {return true;}
}
