package com.mideas.rpg.v2.utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.newdawn.slick.util.ResourceLoader;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF;

public class DropDownMenu {

	private int x_bar;
	private int y_bar;
	private int x_size_bar;
	private TTF font;
	private Arrow arrow;
	private ArrayList<TextMenu> menuList;
	private AlertBackground background;
	protected boolean backgroundActive;
	private int backgroundHeight;
	protected String selectedMenuText = "";
	protected int selectedMenuValue;
	private boolean initText;
	
	public DropDownMenu(float x_bar, float y_bar, float x_size_bar, float x_alert, float y_alert, float x_size_alert, float font_size, float opacity) {
		this.x_bar = (int)x_bar;
		this.y_bar = (int)y_bar;
		this.x_size_bar = (int)x_size_bar;
		this.background = new AlertBackground(x_alert, y_alert, x_size_alert, 0, opacity);
		this.menuList = new ArrayList<TextMenu>();
		this.arrow = new Arrow(this.x_bar+this.x_size_bar-Sprites.arrow_bot.getImageWidth()*Mideas.getDisplayXFactor(), this.y_bar, ArrowDirection.BOT) {
			
			@Override
			public void eventButtonClick() {
				DropDownMenu.this.backgroundActive = !DropDownMenu.this.backgroundActive;
			}
		};
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
		int imageWidth = Sprites.drop_down_menu_left_border.getImageWidth();
		int imageHeight = (int)(Sprites.drop_down_menu_left_border.getImageHeight()*Mideas.getDisplayYFactor());
		Draw.drawQuad(Sprites.drop_down_menu_left_border, this.x_bar, this.y_bar, imageWidth, imageHeight);
		Draw.drawQuad(Sprites.drop_down_menu_middle_border, this.x_bar+imageWidth, this.y_bar, this.x_size_bar-2*imageWidth, imageHeight);
		Draw.drawQuad(Sprites.drop_down_menu_right_border, this.x_bar+this.x_size_bar-imageWidth, this.y_bar, imageWidth, imageHeight);
		this.font.drawStringShadow(this.x_bar+10, this.y_bar+3, this.selectedMenuText, Color.white, Color.black, 1, 0, 0);
		this.arrow.draw();
		if(this.backgroundActive) {
			int i = 0;
			this.background.draw();
			while(i < this.menuList.size()) {
				this.menuList.get(i).draw();
				i++;
			}
		}
	}
	
	public boolean event() {
		if(this.arrow.event()) return true;
		if(this.backgroundActive) {
			int i = 0;
			while(i < this.menuList.size()) {
				if(this.menuList.get(i).event()) {
					this.selectedMenuValue = this.menuList.get(i).getValue();
					this.selectedMenuText = this.menuList.get(i).getText();
					eventButtonClick();
					this.backgroundActive = false;
					return true;
				}
				i++;
			}
			if(Mideas.getHover() && this.background.isHover()) {
				Mideas.setHover(false);
			}
			if(!Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0) {
					this.backgroundActive = false;
				}
			}
		}
		return false;
	}
	
	public void resetMenuList() {
		this.menuList.clear();
		this.background.setHeight(0);
		this.initText = false;
	}
	
	public void addMenu(TextMenu menu) {
		float yShift = 18*Mideas.getDisplayYFactor();
		menu.setValue(this.menuList.size());
		menu.setX(this.background.getX()+this.background.getWidth()/2-menu.getFont().getWidth(menu.getText())/2);
		menu.setY(this.background.getY()+this.menuList.size()*yShift+20*Mideas.getDisplayYFactor());
		this.menuList.add(menu);
		this.backgroundHeight+= this.font.getLineHeight()+9*Mideas.getDisplayYFactor();
		this.background.setHeight(this.backgroundHeight);
		if(!this.initText) {
			this.selectedMenuText = menu.getText();
			this.selectedMenuValue = menu.getValue();
		}
	}
	
	public void update(float x_bar, float y_bar, float x_size_bar, float x_alert, float y_alert, float x_size_alert) {
		this.x_bar = (int)x_bar;
		this.y_bar = (int)y_bar;
		this.x_size_bar = (int)x_size_bar;
		this.background.update(x_alert, y_alert, x_size_alert, (this.font.getLineHeight()+9*Mideas.getDisplayYFactor())*this.menuList.size());
		this.arrow.update(this.x_bar+this.x_size_bar-Sprites.arrow_bot.getImageWidth()*Mideas.getDisplayXFactor(), this.y_bar);
		int i = 0;
		float yShift = 18*Mideas.getDisplayYFactor();
		//System.out.println(this.background.getWidth()/2-this.menuList.get(0).getFont().getWidth(this.menuList.get(i).getText())/2+"  "+(23*Mideas.getDisplayXFactor()-this.menuList.get(i).getFont().getWidth(this.menuList.get(i).getText())/2));
		while(i < this.menuList.size()) {
			this.menuList.get(i).update(this.background.getX()+22*Mideas.getDisplayXFactor(), this.background.getY()+i*yShift+20*Mideas.getDisplayYFactor(), this.background.getWidth()-20*Mideas.getDisplayXFactor(), this.background.getWidth()/2-22*Mideas.getDisplayXFactor()-this.menuList.get(i).getFont().getWidth(this.menuList.get(i).getText())/2);
			i++;
		}
	}
	
	protected void eventButtonClick() {}
}
