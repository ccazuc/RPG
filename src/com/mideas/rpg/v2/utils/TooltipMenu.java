package com.mideas.rpg.v2.utils;

import java.util.ArrayList;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.FontManager;

public class TooltipMenu {

	private Tooltip tooltip;
	private ArrayList<TextMenu> menuList;
	private int x;
	private int y;
	private int x_size;
	private String name;
	private boolean isActive;
	private long lastHover;
	private int tooltipHeight;
	private static TooltipMenu activeMenu;
	private final static int CLOSE_TIMER = 3000;
	private final static int SPACE_BETWEEN_MENU = 18;
	private final static int TOOLTIP_DEFAULT_HEIGHT = 47;
	private final static Colors YELLOW = Colors.decode("#FFC700");
	
	public TooltipMenu(float x, float y, float x_size) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)x_size;
		this.tooltip = new Tooltip(x, y, x_size, 0, .6f);
		this.menuList = new ArrayList<TextMenu>();
	}
	
	public void draw() {
		if(this.isActive) {
			if(System.currentTimeMillis()-this.lastHover > CLOSE_TIMER && !this.tooltip.isHover()) {
				setActive(false);
			}
			this.tooltip.draw();
			FontManager.get("FRIZQT", 14).drawStringShadow(this.x+21*Mideas.getDisplayXFactor(), this.y+15*Mideas.getDisplayYFactor(), this.name, YELLOW, Colors.BLACK, 1, 0, 0);
			int i = 0;
			while(i < this.menuList.size()) {
				this.menuList.get(i).draw();
				i++;
			}
		}
	}
	
	public boolean event() {
		if(this.isActive) {
			if(Mideas.getHover()) {
				int i = 0;
				while(i < this.menuList.size()) {
					if(this.menuList.get(i).event()) {
						this.lastHover = System.currentTimeMillis();
						return true;
					}
					i++;
				}
				if(this.tooltip.isHover()) {
					this.lastHover = System.currentTimeMillis();
					Mideas.setHover(false);
				}
			}
		}
		return false;
	}
	
	public void setActive(boolean we) {
		if(we) {
			if(activeMenu != null) {
				activeMenu.setActive(false);
			}
			activeMenu = this;
			onShow();
			this.lastHover = System.currentTimeMillis();
		}
		else {
			int i = 0;
			while(i < this.menuList.size()) {
				this.menuList.get(i).reset();
				i++;
			}
			activeMenu = null;
			onClose();
		}
		this.isActive = we;
		setActiveAllMenu(we);
	}
	
	public boolean isHover() {
		return this.tooltip.isHover();
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void clearMenu() {
		this.tooltipHeight = 0;
		this.tooltip.setHeight(0);
		this.menuList.clear();
	}
	
	public void setActiveAllMenu(boolean we) {
		int i = 0;
		while(i < this.menuList.size()) {
			this.menuList.get(i).setActive(we);
			i++;
		}
	}
	
	public void addMenu(TextMenu menu) {
		if(this.menuList.size() == 0) {
			this.tooltip.setHeight(TOOLTIP_DEFAULT_HEIGHT*Mideas.getDisplayYFactor());
			this.tooltipHeight+= TOOLTIP_DEFAULT_HEIGHT*Mideas.getDisplayYFactor();
		}
		menu.setX(this.x+7*Mideas.getDisplayXFactor());
		menu.setTextShift(14*Mideas.getDisplayXFactor());
		menu.setWidth(this.x_size-5*Mideas.getDisplayXFactor());
		menu.setY(this.y-11*Mideas.getDisplayYFactor()+SPACE_BETWEEN_MENU*Mideas.getDisplayYFactor()*this.menuList.size());
		this.tooltip.setHeight(this.tooltipHeight+SPACE_BETWEEN_MENU*Mideas.getDisplayYFactor());
		this.tooltipHeight+= SPACE_BETWEEN_MENU*Mideas.getDisplayYFactor();
		this.menuList.add(menu);
	}
	
	public void updateSize(float x, float y, float x_size) {
		this.x = (int)x;
		this.y = (int)y;
		this.tooltip.update(x, y, x_size, TOOLTIP_DEFAULT_HEIGHT*Mideas.getDisplayYFactor());
		this.tooltipHeight = (int)(TOOLTIP_DEFAULT_HEIGHT*Mideas.getDisplayYFactor());
		int i = 0;
		while(i < this.menuList.size()) {
			this.menuList.get(i).update(x+7*Mideas.getDisplayXFactor(), this.y-11*Mideas.getDisplayYFactor()+this.tooltipHeight, x_size-7*Mideas.getDisplayXFactor(), 14*Mideas.getDisplayXFactor());
			this.tooltipHeight+= SPACE_BETWEEN_MENU*Mideas.getDisplayYFactor();
			i++;
		}
		this.tooltip.setHeight(this.tooltipHeight);
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public boolean isActive() {
		return this.isActive;
	}
	
	public void onClose() {}
	
	public void onShow() {}
}
