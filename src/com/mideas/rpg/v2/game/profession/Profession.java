package com.mideas.rpg.v2.game.profession;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.utils.Draw;
import com.mideas.rpg.v2.utils.ScrollBar;
import com.mideas.rpg.v2.utils.Texture;

public class Profession {

	private ArrayList<Category> categoryList = new ArrayList<Category>();
	private String name;
	private int id;
	private int playerLevel = 370;
	private CraftableItem selectedItem;
	private ScrollBar scrollBar;
	private boolean init;
	
	public Profession(int id, String name, Category category1, Category category2, Category category3, Category category4, Category category5, Category category6, Category category7, Category category8) {
		this.id = id;
		this.name = name;
		this.addCategory(category1);
		this.addCategory(category2);
		this.addCategory(category3);
		this.addCategory(category4);
		this.addCategory(category5);
		this.addCategory(category6);
		this.addCategory(category7);
		this.addCategory(category8);
	}
	
	public ArrayList<Category> getCategoryList() {
		return this.categoryList;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void addCategory(Category category) {
		if(category != null) {
			this.categoryList.add(category);
		}
	}
	
	public void draw(int x, int y) {
		if(!this.init) {
			this.scrollBar = new ScrollBar(x+800, y+100, 100);
			this.init = true;
		}
		Draw.drawQuad(Sprites.craft_frame, x, y);
		this.scrollBar.draw();
		x+= 26;
		y+= 99;
		int i = 0;
		int j = 0;
		int yShift = 0;
		int yShiftHeight = 17;
		while(i < this.categoryList.size()) {
			j = 0;
			if(yShift+yShiftHeight <= 140) {
				drawButton(this.categoryList.get(i), x, y+yShift);
				if(this.categoryList.get(i).getMouseDown()) {
					TTF2.craft.drawString(x+22, y+2+yShift, this.categoryList.get(i).getName(), getColorCategory(this.categoryList.get(i)));
				}
				else {
					TTF2.craft.drawString(x+20, y+yShift, this.categoryList.get(i).getName(), getColorCategory(this.categoryList.get(i)));
				}
			}
			yShift+= yShiftHeight;
			if(this.categoryList.get(i).getExpand()) {
				while(j < this.categoryList.get(i).getCraftList().size()) {
					if(yShift+yShiftHeight <= 140) {
						if(this.categoryList.get(i).getCraftList().get(j) == this.selectedItem) {
							Draw.drawQuad(getColor(this.categoryList.get(i).getCraftList().get(j).getLevel()), x-5, y+yShift);
							drawSelectedItem(this.selectedItem);
						}
						if(this.categoryList.get(i).getCraftList().get(j).getMouseDown()) {
							TTF2.craft.drawString(x+29, y+2+yShift, "Poison mortel III", getFontColor(this.categoryList.get(i).getCraftList().get(j)));
						}
						else {
							TTF2.craft.drawString(x+27, y+yShift, "Poison mortel III", getFontColor(this.categoryList.get(i).getCraftList().get(j)));
						}
					}
					j++;
					yShift+= yShiftHeight;
					if(yShift >= 140) {
						break;
					}
				}
			}
			if(yShift >= 140) {
				break;
			}
			i++;
		}
	}
	
	public void event(int x, int y) {
		this.scrollBar.event();
		x+= 26;
		y+= 98;
		int i = 0;
		int j = 0;
		int yShift = 0;
		int yShiftHeight = 17;
		float borderHeight = Sprites.craft_orange_selection.getImageHeight()*Mideas.getDisplayXFactor()-2;
		while(i < this.categoryList.size()) {
			j = 0;
			checkMouseEventCategory(x, y+1+yShift, Sprites.craft_orange_selection.getImageWidth()*Mideas.getDisplayXFactor(), borderHeight, this.categoryList.get(i));
			/*if(click(x, y+1+yShift, Sprites.expand_category_craft.getImageWidth()*Mideas.getDisplayXFactor(), Sprites.expand_category_craft.getImageHeight()*Mideas.getDisplayXFactor())) {
				this.categoryList.get(i).setExpand(!this.categoryList.get(i).getExpand());
			}*/
			yShift+= yShiftHeight;
			if(this.categoryList.get(i).getExpand()) {
				while(j < this.categoryList.get(i).getCraftList().size()) {
					checkMouseEventItem(x, y+yShift, Sprites.craft_orange_selection.getImageWidth()*Mideas.getDisplayXFactor(), borderHeight, this.categoryList.get(i).getCraftList().get(j));
					j++;
					yShift+= yShiftHeight;
					if(yShift >= 140) {
						break;
					}
				}
			}
			if(yShift >= 140) {
				break;
			}
			i++;
		}
	}
	
	private static void drawSelectedItem(CraftableItem item) {
		
	}
	
	private void checkMouseEventItem(float x, float y, float x_size, float y_size, CraftableItem item) {
		if(Mideas.mouseX() >= x && Mideas.mouseX() <= x+x_size && Mideas.mouseY() >= y && Mideas.mouseY() <= y+y_size) {
			if(Mouse.getEventButton() == 0) {
				if(Mouse.getEventButtonState()) {
					item.setMouseDown(true);
				}
				else if(item.getMouseDown()) {
					this.selectedItem = item;
					item.setMouseDown(false);
				}
			}
			else if(Mouse.getEventButton() == 1) {
				if(Mouse.getEventButtonState()) {
					item.setMouseDown(true);
				}
				else {
					item.setMouseDown(false);
				}
			}
			item.setMouseHover(true);
		}
		else if(item.getMouseHover()) {
			item.setMouseHover(false);
		}
		if(item.getMouseDown()) {
			if(Mouse.getEventButton() == 0) {
				if(!Mouse.getEventButtonState()) {
					item.setMouseDown(false);
				}
			}
			else if(Mouse.getEventButton() == 1) {
				if(!Mouse.getEventButtonState()) {
					item.setMouseDown(false);
				}
			}
		}
	}
	
	private void checkMouseEventCategory(float x, float y, float x_size, float y_size, Category category) {
		if(Mideas.mouseX() >= x && Mideas.mouseX() <= x+x_size && Mideas.mouseY() >= y && Mideas.mouseY() <= y+y_size) {
			if(Mouse.getEventButton() == 0) {
				if(Mouse.getEventButtonState()) {
					category.setMouseDown(true);
				}
				else if(category.getMouseDown()) {
					category.setMouseDown(false);
					category.setExpand(!category.getExpand());
				}
			}
			else if(Mouse.getEventButton() == 1) {
				if(Mouse.getEventButtonState()) {
					category.setMouseDown(true);
				}
				else {
					category.setMouseDown(false);
				}
			}
			category.setMouseHover(true);
		}
		else if(category.getMouseHover()) {
			category.setMouseHover(false);
		}
		if(category.getMouseDown()) {
			if(Mouse.getEventButton() == 0) {
				if(!Mouse.getEventButtonState()) {
					category.setMouseDown(false);
				}
			}
			else if(Mouse.getEventButton() == 1) {
				if(!Mouse.getEventButtonState()) {
					category.setMouseDown(false);
				}
			}
		}
	}
	
	private Color getColorCategory(Category category) {
		if(category.getMouseHover()) {
			return Color.white;
		}
		return Color.decode("#DDB500");
		
	}
	
	private Texture getColor(int itemLevel) {
		if(this.playerLevel-itemLevel < 15) {
			return Sprites.craft_orange_selection;
		}
		if(this.playerLevel-itemLevel < 30) {
			return Sprites.craft_yellow_selection;
		}
		if(this.playerLevel-itemLevel < 45) {
			return Sprites.craft_green_selection;
		}
		if(this.playerLevel-itemLevel > 45) {
			return Sprites.craft_grey_selection;
		}
		return null;
	}
	
	private Color getFontColor(CraftableItem item) {
		int itemLevel = item.getLevel();
		if(item.getMouseHover() || this.selectedItem == item) {
			return Color.white;
		}
		if(this.playerLevel-itemLevel < 15) {
			return Color.decode("#A8542A");
		}
		if(this.playerLevel-itemLevel < 30) {
			return Color.decode("#B9B700");
		}
		if(this.playerLevel-itemLevel < 45) {
			return Color.decode("#2D852D");
		}
		if(this.playerLevel-itemLevel > 45) {
			return Color.decode("#585758");
		}
		return null;
	}
	
	private void drawButton(Category category, int x, int y) {
		if(category.getExpand()) {
			if(category.getMouseHover()) {
				Draw.drawQuad(Sprites.reduce_category_craft_hover, x, y+1);
			}
			else {
				Draw.drawQuad(Sprites.reduce_category_craft, x, y+1);
			}
		}
		else {
			if(category.getMouseHover()) {
				Draw.drawQuad(Sprites.expand_category_craft_hover, x, y+1);
			}
			else {
				Draw.drawQuad(Sprites.expand_category_craft, x, y+1);
			}
		}
	}
	
	public int getPlayerLevel() {
		return this.playerLevel;
	}
}
