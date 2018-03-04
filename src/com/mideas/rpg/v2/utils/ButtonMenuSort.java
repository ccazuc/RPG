package com.mideas.rpg.v2.utils;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.utils.render.Draw;
import com.mideas.rpg.v2.utils.render.Sprites;
import com.mideas.rpg.v2.utils.render.TTF;
import com.mideas.rpg.v2.FontManager;

public class ButtonMenuSort {

	private short x;
	private short y;
	private short xSave;
	private short ySave;
	private short width;
	private short height;
	private short widthSave;
	private short heightSave;
	private String text;
	private boolean buttonHover;
	private boolean buttonDown;
	private TTF font;
	private Frame parentFrame;
	
	public ButtonMenuSort(float x, float y, float width, float height, String text, TTF font) {
		this.x = (short)x;
		this.y = (short)y;
		this.widthSave = (short)width;
		this.heightSave = (short)height;
		this.height = (short)height;
		this.width = (short)width;
		this.text = text;
		this.font = font;
	}
	
	public ButtonMenuSort(Frame parentFrame, float x, float y, float width, float height, String text, TTF font)
	{
		this.parentFrame = parentFrame;
		this.xSave = (short)x;
		this.ySave = (short)y;
		this.widthSave = (short)width;
		this.heightSave = (short)height;
		this.height = (short)height;
		this.width = (short)width;
		this.text = text;
		this.font = font;
	}
	
	public ButtonMenuSort(float x, float y, float width, float height, String text, float font_size) {
		this(x, y, width, height, text, FontManager.get("FRIZQT", font_size));
	}
	
	public ButtonMenuSort(float x, float y, float width, String text, float font_size) {
		this(x, y, width, Sprites.button_menu_sort_middle_part.getImageHeight()*Mideas.getDisplayYFactor(), text, font_size);
	}
	
	public void initParentFrame(Frame parentFrame)
	{
		this.parentFrame = parentFrame;
		updateSize();
	}
	
	public void drawTexturePart() {
		int imageWidth = Sprites.button_menu_sort_left_part.getImageWidth();
		Draw.drawQuadPart(Sprites.button_menu_sort, this.x, this.y, imageWidth, this.height, 0, 0, 6, 26);					//left_border
		Draw.drawQuadPart(Sprites.button_menu_sort, this.x+imageWidth, this.y, this.width-2*imageWidth, this.height, 8, 0, 1, 26);		//midle_border
		Draw.drawQuadPart(Sprites.button_menu_sort, this.x+this.width-imageWidth, this.y, imageWidth, this.height, 14, 0, 6, 26);		//right_border
	}
	
	public void drawStringPart() {
		if(this.buttonDown) {
			this.font.drawStringShadowPart(this.x+9*Mideas.getDisplayXFactor()+2, this.y+this.height/2-this.font.getLineHeight()/2+2, this.text, Color.WHITE, Color.BLACK, 1, 0, 0);
		}
		else {
			this.font.drawStringShadowPart(this.x+9*Mideas.getDisplayXFactor(), this.y+this.height/2-this.font.getLineHeight()/2, this.text, Color.WHITE, Color.BLACK, 1, 0, 0);
		}
	}
	
	public void drawHoverPart() {
		if(this.buttonHover) {
			Draw.drawQuadBlendPart(Sprites.button_menu_hover, this.x-3*Mideas.getDisplayXFactor(), this.y-3*Mideas.getDisplayYFactor(), this.width+7*Mideas.getDisplayYFactor(), this.height+9*Mideas.getDisplayYFactor());
		}
	}
	
	public void draw() {
		int imageWidth = Sprites.button_menu_sort_left_part.getImageWidth();
		/*Draw.drawQuad(Sprites.button_menu_sort_left_part, this.x, this.y, imageWidth, this.y_size);
		Draw.drawQuad(Sprites.button_menu_sort_middle_part, this.x+imageWidth, this.y, this.x_size-2*imageWidth, this.y_size);
		Draw.drawQuad(Sprites.button_menu_sort_right_part, this.x+this.x_size-imageWidth, this.y, imageWidth, this.y_size);*/

		Sprites.button_menu_sort.drawBegin();
		Draw.drawQuadPart(Sprites.button_menu_sort, this.x, this.y, imageWidth, this.height, 0, 0, 6, 26);					//left_border
		Draw.drawQuadPart(Sprites.button_menu_sort, this.x+imageWidth, this.y, this.width-2*imageWidth, this.height, 8, 0, 1, 26);		//midle_border
		Draw.drawQuadPart(Sprites.button_menu_sort, this.x+this.width-imageWidth, this.y, imageWidth, this.height, 14, 0, 6, 26);		//right_border
		Sprites.button_menu_sort.drawEnd();
		
		if(this.buttonDown) {
			this.font.drawStringShadow(this.x+9*Mideas.getDisplayXFactor()+2, this.y+this.height/2-this.font.getLineHeight()/2+2, this.text, Color.WHITE, Color.BLACK, 1, 0, 0);
		}
		else {
			this.font.drawStringShadow(this.x+9*Mideas.getDisplayXFactor(), this.y+this.height/2-this.font.getLineHeight()/2, this.text, Color.WHITE, Color.BLACK, 1, 0, 0);
		}
		if(this.buttonHover) {
			Draw.drawQuadBlend(Sprites.button_menu_hover, this.x-3*Mideas.getDisplayXFactor(), this.y-3*Mideas.getDisplayYFactor(), this.width+7*Mideas.getDisplayYFactor(), this.height+9*Mideas.getDisplayYFactor());
		}
	}
	
	public boolean mouseEvent() {
		if(Mideas.getHover() && Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x+this.width && Mideas.mouseY() >= this.y && Mideas.mouseY() <= this.y+Sprites.button_menu_sort_left_part.getImageHeight()*Mideas.getDisplayYFactor()) {
			this.buttonHover = true;
			Mideas.setHover(false);
		}
		else  {
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
	
	public void updateSize()
	{
		this.x = (short)(this.parentFrame.getX() + this.xSave * Mideas.getDisplayXFactor());
		this.y = (short)(this.parentFrame.getY() + this.ySave * Mideas.getDisplayYFactor());
		this.width = (short)(this.widthSave * Mideas.getDisplayXFactor());
		this.height = (short)(this.heightSave * Mideas.getDisplayYFactor());
	}
	
	public void update(float x, float y) {
		update(x, y, this.widthSave*Mideas.getDisplayXFactor(), this.heightSave*Mideas.getDisplayYFactor());
	}
	
	public void update(float x, float y, float width) {
		update(x, y, width, Sprites.button_menu_sort_left_part.getImageHeight()*Mideas.getDisplayYFactor());
	}

	public void update(float x, float y, float width, float height) {
		this.x = (short)x;
		this.y = (short)y;
		this.width = (short)width;
		this.height = (short)height;
	}
	
	protected void eventButtonClick() {}
 } 
