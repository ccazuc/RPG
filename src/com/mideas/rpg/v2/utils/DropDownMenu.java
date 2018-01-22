package com.mideas.rpg.v2.utils;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.utils.render.Draw;
import com.mideas.rpg.v2.utils.render.Sprites;
import com.mideas.rpg.v2.utils.render.TTF;
import com.mideas.rpg.v2.FontManager;

public class DropDownMenu {

	private int x_bar;
	private int y_bar;
	private int x_size_bar;
	private boolean isActive;
	private final TTF font;
	private final Arrow arrow;
	private final ArrayList<TextMenu> menuList;
	private final AlertBackground background;
	protected boolean backgroundActive;
	private int backgroundHeight;
	protected String selectedMenuText = "";
	protected int selectedMenuValue;
	private boolean initText;
	private int backgroundDefaultHeight;
	private boolean clickableBar;
	private boolean barMouseDown;
	private boolean barMouseHover;
	private final static short ARROW_WIDTH = 20;
	private final static short ARROW_HEIGHT = 19;
	
	public DropDownMenu(float x_bar, float y_bar, float x_size_bar, float x_alert, float y_alert, float x_size_alert, float font_size, float opacity) {
		this.x_bar = (int)x_bar;
		this.y_bar = (int)y_bar;
		this.x_size_bar = (int)x_size_bar;
		this.background = new AlertBackground(x_bar-10*Mideas.getDisplayXFactor(), y_alert, x_size_alert, 0, opacity);
		this.menuList = new ArrayList<TextMenu>();
		this.arrow = new Arrow(this.x_bar+this.x_size_bar-ARROW_WIDTH*Mideas.getDisplayXFactor()-2, this.y_bar+2, ARROW_WIDTH*Mideas.getDisplayXFactor(), ARROW_HEIGHT*Mideas.getDisplayYFactor(), ArrowDirection.BOT) {
			
			@Override
			public void onLeftClickUp() {
				DropDownMenu.this.backgroundActive = !DropDownMenu.this.backgroundActive;
			}
		};
		this.font = FontManager.get("FRIZQT", font_size);
	}
	
	public DropDownMenu(float x_bar, float y_bar, float x_size_bar, float x_alert, float y_alert, float x_size_alert, float font_size, float opacity, int backgroundDefaultHeight, boolean clickable_bar) {
		this.x_bar = (int)x_bar;
		this.y_bar = (int)y_bar;
		this.x_size_bar = (int)x_size_bar;
		this.background = new AlertBackground(x_alert, y_alert, x_size_alert, 0, opacity);
		this.menuList = new ArrayList<TextMenu>();
		this.arrow = new Arrow(this.x_bar+this.x_size_bar-ARROW_WIDTH*Mideas.getDisplayXFactor()-2, this.y_bar+2, ARROW_WIDTH*Mideas.getDisplayXFactor(), ARROW_HEIGHT*Mideas.getDisplayYFactor(), ArrowDirection.BOT) {
			
			@Override
			public void onLeftClickUp() {
				DropDownMenu.this.backgroundActive = !DropDownMenu.this.backgroundActive;
			}
		};
		this.backgroundDefaultHeight = backgroundDefaultHeight;
		this.font = FontManager.get("FRIZQT", font_size);
		this.clickableBar = clickable_bar;
	}
	
	public void draw() {
		int imageWidth = Sprites.drop_down_menu_left_border.getImageWidth();
		int imageHeight = (int)(Sprites.drop_down_menu_left_border.getImageHeight()*Mideas.getDisplayYFactor());
		Draw.drawQuad(Sprites.drop_down_menu_left_border, this.x_bar, this.y_bar, imageWidth, imageHeight);
		Draw.drawQuad(Sprites.drop_down_menu_middle_border, this.x_bar+imageWidth, this.y_bar, this.x_size_bar-2*imageWidth, imageHeight);
		Draw.drawQuad(Sprites.drop_down_menu_right_border, this.x_bar+this.x_size_bar-imageWidth, this.y_bar, imageWidth, imageHeight);
		this.font.drawStringShadow(this.x_bar+10, this.y_bar+3, this.selectedMenuText, Color.WHITE, Color.BLACK, 1, 0, 0);
		if(this.clickableBar) {
			if(this.barMouseHover) {
				Draw.drawQuadBlend(Sprites.button_menu_hover, this.x_bar-3, this.y_bar-3, this.x_size_bar, 35*Mideas.getDisplayYFactor());
			}
		}
		this.arrow.draw();
		if(this.backgroundActive) {
			int i = 0;
			this.background.draw();
			while(i < this.menuList.size()) {
				this.menuList.get(i).draw();
				if(this.selectedMenuValue == this.menuList.get(i).getValue()) {
					Draw.drawQuad(Sprites.check_box_enable, this.menuList.get(i).getX()+this.menuList.get(i).getTextShift()-(Sprites.check_box_enable.getImageWidth())*Mideas.getDisplayXFactor(), this.menuList.get(i).getY(), 20*Mideas.getDisplayXFactor(), 20*Mideas.getDisplayYFactor());
				}
				i++;
			}
		}
	}
	
	public boolean event() {
		if(this.arrow.event()) return true;
		if(this.clickableBar) {
			if(Mideas.getHover() && Mideas.mouseX() >= this.x_bar && Mideas.mouseX() <= this.x_bar+this.x_size_bar && Mideas.mouseY() >= this.y_bar && Mideas.mouseY() <= this.y_bar+20*Mideas.getDisplayYFactor()) {
				this.barMouseHover = true;
				Mideas.setHover(false);
			}
			else {
				this.barMouseHover = false;
			}
			if(this.barMouseHover) {
				if(Mouse.getEventButtonState()) {
					if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
						this.barMouseDown = true;
					}
				}
				else if(this.barMouseDown) {
					if(Mouse.getEventButton() == 0) {
						this.barMouseDown = false;
						barEventButtonClick();
						return true;
					}
					else if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
						this.barMouseDown = false;
					}
				}
			}
			else if(!Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
					this.barMouseDown = false;
				}
			}
		}
		if(!this.backgroundActive) {
			return false;
		}
		int i = 0;
		while(i < this.menuList.size()) {
			if(this.menuList.get(i).event()) {
				this.selectedMenuValue = this.menuList.get(i).getValue();
				this.selectedMenuText = this.menuList.get(i).getText();
				menuEventButtonClick();
				this.backgroundActive = false;
				return true;
			}
			i++;
		}
		this.background.isHover();
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
	
	public void addMenu(TextMenu menu) {
		float yShift = 18*Mideas.getDisplayYFactor();
		menu.setValue(this.menuList.size());
		menu.setWidth(this.background.getWidth()-20);
		menu.setX(this.background.getX()+10*Mideas.getDisplayXFactor());
		//menu.setTextShift(this.background.getWidth()/2-50*Mideas.getDisplayXFactor());
		menu.setTextShift(30*Mideas.getDisplayXFactor());
		menu.setY(this.background.getY()+this.menuList.size()*yShift+20*Mideas.getDisplayYFactor());
		this.menuList.add(menu);
		this.backgroundHeight+= this.font.getLineHeight()+7*Mideas.getDisplayYFactor();
		this.background.setHeight(this.backgroundHeight);
		if(!this.initText) {
			this.backgroundHeight+= this.backgroundDefaultHeight*Mideas.getDisplayYFactor();
			this.selectedMenuText = menu.getText();
			this.selectedMenuValue = menu.getValue();
			this.initText = true;
		}
		if(menu.getFont().getWidth(menu.getText())+30 >= this.background.getWidth()-25*Mideas.getDisplayXFactor()) {
			this.background.setWidth(menu.getFont().getWidth(menu.getText())+70*Mideas.getDisplayXFactor());
			int j = 0;
			while(j < this.menuList.size()) {
				this.menuList.get(j).setWidth(this.background.getWidth()-20*Mideas.getDisplayXFactor());
				j++;
			}
		}
	}
	
	public void setActive(boolean we) {
		int i = 0;
		while(i < this.menuList.size()) {
			this.menuList.get(i).setActive(we);
			i++;
		}
		this.isActive = we;
	}
	
	public void update(float x_bar, float y_bar, float x_size_bar, float x_alert, float y_alert, float x_size_alert) {
		this.x_bar = (int)x_bar;
		this.y_bar = (int)y_bar;
		this.x_size_bar = (int)x_size_bar;
		this.background.update(x_bar-10*Mideas.getDisplayXFactor(), y_alert, x_size_alert, (23*Mideas.getDisplayYFactor())*this.menuList.size()+this.backgroundDefaultHeight*Mideas.getDisplayYFactor());
		this.arrow.update(this.x_bar+this.x_size_bar-ARROW_WIDTH*Mideas.getDisplayXFactor()-2, this.y_bar+2);
		int i = 0;
		float yShift = 18*Mideas.getDisplayYFactor();
		//System.out.println(this.background.getWidth()/2-this.menuList.get(0).getFont().getWidth(this.menuList.get(i).getText())/2+"  "+(23*Mideas.getDisplayXFactor()-this.menuList.get(i).getFont().getWidth(this.menuList.get(i).getText())/2));
		while(i < this.menuList.size()) {
			this.menuList.get(i).update(this.background.getX()+10*Mideas.getDisplayXFactor(), this.background.getY()+i*yShift+20*Mideas.getDisplayYFactor(), this.background.getWidth()-20*Mideas.getDisplayXFactor(), 30*Mideas.getDisplayXFactor());
			//System.out.println((this.menuList.get(i).getFont().getWidth(this.menuList.get(i).getText())+30*Mideas.getDisplayXFactor())+" "+(this.background.getWidth()-10*Mideas.getDisplayXFactor()));
			if(this.menuList.get(i).getFont().getWidth(this.menuList.get(i).getText())+30 >= this.background.getWidth()-25*Mideas.getDisplayXFactor()) {
				this.background.setWidth(this.menuList.get(i).getFont().getWidth(this.menuList.get(i).getText())+70*Mideas.getDisplayXFactor());
				int j = 0;
				while(j <= i) {
					this.menuList.get(j).setWidth(this.background.getWidth()-20*Mideas.getDisplayXFactor());
					j++;
				}
			}
			i++;
		}
	}
	
	public void setMenuText(String name) {
		this.selectedMenuText = name;
	}
	
	public void setValue(int value) {
		this.selectedMenuValue = value;
		this.selectedMenuText = this.menuList.get(value).getText();
	}
	
	protected void menuEventButtonClick() {}
	protected void barEventButtonClick() {}
}
