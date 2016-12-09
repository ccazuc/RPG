package com.mideas.rpg.v2.utils;

import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.TTF;
import com.mideas.rpg.v2.FontManager;

public class Alert {

	private Button button;
	private String text;
	private String[] formatedText;
	private int baseX;
	private int baseY;
	private int x;
	private int y;
	private int x_size_alert;
	private AlertBackground background;
	private boolean isActive;
	private static TTF font = FontManager.get("FRIZQT", 24);
	private final static int BUTTON_WIDTH = 250;
	private final static int BUTTON_HEIGHT = 35;
	
	public Alert(String text, float x, float y, float x_size_alert, int font_size, String button_text) {
		this.text = text;
		this.x = Display.getWidth()/2+(int)x;
		this.y = Display.getHeight()/2+(int)y;
		this.baseX = (int)x;
		this.baseY = (int)y;
		this.x_size_alert = (int)x_size_alert;
		this.background = new AlertBackground(this.x, this.y, x_size_alert, 110*Mideas.getDisplayYFactor()+(font.getLineHeight()+3)*(font.getWidth(this.text)/(x_size_alert-30f)), .7f);
		this.button = new Button(this.x+x_size_alert/2-BUTTON_WIDTH*Mideas.getDisplayXFactor()/2, this.background.getY()+this.background.getHeight()-70*Mideas.getDisplayYFactor(), BUTTON_WIDTH*Mideas.getDisplayXFactor(), BUTTON_HEIGHT*Mideas.getDisplayYFactor(), button_text, font_size, 2);
		setText(this.text);
	}
	
	public void draw() {
		if(!this.isActive) {
			return;
		}
		int i = 0;
		int y_shift = 0;
		this.background.draw();
		font.drawBegin();
		if(this.formatedText.length == 1) {
			font.drawStringShadowPart(this.x+this.background.getWidth()/2-font.getWidth(this.formatedText[0])/2, this.y+14*Mideas.getDisplayYFactor(), this.formatedText[0], Color.YELLOW, Color.BLACK, 2, 2, 1);
		}
		else {
			while(i < this.formatedText.length) {
				if(i == this.formatedText.length-1) {
					font.drawStringShadowPart(this.x+this.x_size_alert/2-font.getWidth(this.formatedText[i])/2, this.y+14*Mideas.getDisplayYFactor()+y_shift, this.formatedText[i], Color.YELLOW, Color.BLACK, 2, 2, 1);
				}
				else {
					font.drawStringShadowPart(this.x+10, this.y+14*Mideas.getDisplayYFactor()+y_shift, this.formatedText[i], Color.YELLOW, Color.BLACK, 2, 2, 1);
				}
				y_shift+= font.getLineHeight()+3;
				i++;
			}
		}
		font.drawEnd();
		this.button.draw();
	}
	
	public boolean event() {
		if(!this.isActive) {
			return false;
		}
		if(this.button.event()) {
			this.button.reset();
			this.isActive = false;
			return true;
		}
		return false;
	}
	
	public void update(float x, float y, float x_size) {
		this.x = (int)x;
		this.y = (int)y;
		this.background.setWidth(x_size);
		this.button.setX(this.x+this.background.getWidth()/2-BUTTON_WIDTH*Mideas.getDisplayXFactor()/2);
		this.button.setButtonWidth(BUTTON_WIDTH*Mideas.getDisplayXFactor());
		this.button.setButtonHeight(BUTTON_HEIGHT*Mideas.getDisplayYFactor());
		this.background.setY(this.y);
		this.background.setX(this.x);
		setText(this.text);
	}
	
	public void setText(String text) {
		this.text = text;
		int i = 0;
		int j = 0;
		int k = 0;
		int width = 0;
		float lineNumber = (font.getWidth(this.text)/(this.background.getWidth()-30f));
		this.formatedText = new String[(int)Math.ceil(lineNumber)];
		if(lineNumber > 1) {
			while(i < this.text.length()) {
				if(width >= this.background.getWidth()-30) {
					if(this.text.charAt(i) != ' ') {
						i = checkSpace(this.text, i);
						this.formatedText[k] = this.text.substring(j, i);
						j = i;
						i++;
						k++;
						width = 0;
						continue;
					}
					this.formatedText[k] = this.text.substring(j, i);
					j = i;
					i++;
					k++;
					width = 0;
				}
				width+= font.getWidth(this.text.charAt(i));
				i++;
			}
		}
		if(k < lineNumber) {
			this.formatedText[k] = this.text.substring(j);
		}
		i = 0;
		while(i < this.formatedText.length) {
			i++;
		}
		this.background.setHeight(110*Mideas.getDisplayYFactor()+(font.getLineHeight()+3)*lineNumber);
		this.button.setY(this.background.getY()+this.background.getHeight()-70*Mideas.getDisplayYFactor());
	}
	
	public int getBaseX() {
		return this.baseX;
	}
	
	public int getBaseY() {
		return this.baseY;
	}
	
	public void setActive() {
		this.isActive = true;
		this.event();
	}
	
	public void setInactive() {
		this.isActive = false;
		this.button.reset();
	}
	
	public boolean isActive() {
		return this.isActive;
	}
	
	public float getWidth() {
		return this.background.getWidth();
	}
	
	private static int checkSpace(String text, int i) {
		while(i > 0 && text.charAt(i) != ' ' && text.charAt(i) != ',') {
			i--;
		}
		return i;
	}
}
