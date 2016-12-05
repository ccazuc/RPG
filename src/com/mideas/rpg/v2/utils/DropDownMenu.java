package com.mideas.rpg.v2.utils;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF;
import com.mideas.rpg.v2.FontManager;

public class DropDownMenu {

	private int x_bar;
	private int y_bar;
	private int x_size_bar;
	private boolean isActive;
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
		this.font = FontManager.get("FRIZQT", font_size);
	}
	
	public void draw() {
		if(!this.isActive) {
			return;
		}
		int imageWidth = Sprites.drop_down_menu_left_border.getImageWidth();
		int imageHeight = (int)(Sprites.drop_down_menu_left_border.getImageHeight()*Mideas.getDisplayYFactor());
		Draw.drawQuad(Sprites.drop_down_menu_left_border, this.x_bar, this.y_bar, imageWidth, imageHeight);
		Draw.drawQuad(Sprites.drop_down_menu_middle_border, this.x_bar+imageWidth, this.y_bar, this.x_size_bar-2*imageWidth, imageHeight);
		Draw.drawQuad(Sprites.drop_down_menu_right_border, this.x_bar+this.x_size_bar-imageWidth, this.y_bar, imageWidth, imageHeight);
		this.font.drawStringShadow(this.x_bar+10, this.y_bar+3, this.selectedMenuText, Color.WHITE, Color.BLACK, 1, 0, 0);
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
		if(!this.isActive) {
			return false;
		}
		if(this.arrow.event()) return true;
		if(!this.backgroundActive) {
			return false;
		}
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
		return false;
	}
	
	public void resetMenuList() {
		this.menuList.clear();
		this.background.setHeight(0);
		this.initText = false;
		this.backgroundHeight = 0;
	}
	
	public void addMenu(TextMenu menu) { //TODO: update all menu's width if this menu is larger than current width
		float yShift = 18*Mideas.getDisplayYFactor();
		menu.setValue(this.menuList.size());
		menu.setX(this.background.getX()+10*Mideas.getDisplayXFactor());
		menu.setTextShift(this.background.getWidth()/2-80*Mideas.getDisplayXFactor());
		menu.setY(this.background.getY()+this.menuList.size()*yShift+20*Mideas.getDisplayYFactor());
		this.menuList.add(menu);
		this.backgroundHeight+= this.font.getLineHeight()+7*Mideas.getDisplayYFactor();
		this.background.setHeight(this.backgroundHeight);
		if(!this.initText) {
			this.selectedMenuText = menu.getText();
			this.selectedMenuValue = menu.getValue();
			this.initText = true;
		}
	}
	
	public void setActive(boolean we) {
		if(we) {
			int i = 0;
			while(i < this.menuList.size()) {
				this.menuList.get(i).setActive(true);
				i++;
			}
		}
		else {
			int i = 0;
			while(i < this.menuList.size()) {
				this.menuList.get(i).setActive(false);
				i++;
			}
		}
		this.isActive = we;
	}
	
	public void update(float x_bar, float y_bar, float x_size_bar, float x_alert, float y_alert, float x_size_alert) {
		this.x_bar = (int)x_bar;
		this.y_bar = (int)y_bar;
		this.x_size_bar = (int)x_size_bar;
		this.background.update(x_alert, y_alert, x_size_alert, (this.font.getLineHeight()+7*Mideas.getDisplayYFactor())*this.menuList.size());
		this.arrow.update(this.x_bar+this.x_size_bar-Sprites.arrow_bot.getImageWidth()*Mideas.getDisplayXFactor(), this.y_bar);
		int i = 0;
		float yShift = 18*Mideas.getDisplayYFactor();
		//System.out.println(this.background.getWidth()/2-this.menuList.get(0).getFont().getWidth(this.menuList.get(i).getText())/2+"  "+(23*Mideas.getDisplayXFactor()-this.menuList.get(i).getFont().getWidth(this.menuList.get(i).getText())/2));
		while(i < this.menuList.size()) {
			this.menuList.get(i).update(this.background.getX()+10*Mideas.getDisplayXFactor(), this.background.getY()+i*yShift+20*Mideas.getDisplayYFactor(), this.background.getWidth()-20*Mideas.getDisplayXFactor(), this.background.getWidth()/2-10*Mideas.getDisplayXFactor());
			i++;
		}
	}
	
	public void setMenuText(String name) {
		this.selectedMenuText = name;
	}
	
	public void setValue(int value) {
		this.selectedMenuValue = value;
	}
	
	protected void eventButtonClick() {}
}
