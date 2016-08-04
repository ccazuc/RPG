package com.mideas.rpg.v2.game.profession;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.utils.Draw;
import com.mideas.rpg.v2.utils.Texture;

public class Profession {

	private ArrayList<Category> categoryList = new ArrayList<Category>();
	private String name;
	private int id;
	private int playerLevel = 370;
	private CraftableItem selectedItem;
	
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
		Draw.drawQuad(Sprites.craft_frame, x, y);
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
				TTF2.craft.drawString(x+20, y+yShift, this.categoryList.get(i).getName(), Color.decode("#DDB500"));
			}
			yShift+= yShiftHeight;
			if(this.categoryList.get(i).getExpand()) {
				while(j < this.categoryList.get(i).getCraftList().size()) {
					if(yShift+yShiftHeight <= 140) {
						if(this.categoryList.get(i).getCraftList().get(j) == this.selectedItem) {
							Draw.drawQuad(getColor(this.categoryList.get(i).getCraftList().get(j).getLevel()), x-5, y+yShift);
							drawSelectedItem(this.selectedItem);
						}
						//TTF2.craft.drawString(x+27, y+yShift, this.categoryList.get(i).getCraftList().get(j).getItem().getStuffName(), Color.white);
						TTF2.craft.drawString(x+27, y+yShift, "Poison mortel III", Color.white);
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
		x+= 26;
		y+= 98;
		int i = 0;
		int j = 0;
		int yShift = 0;
		int yShiftHeight = 17;
		while(i < this.categoryList.size()) {
			j = 0;
			if(click(x, y+1+yShift, Sprites.expand_category_craft.getImageWidth()*Mideas.getDisplayXFactor(), Sprites.expand_category_craft.getImageHeight()*Mideas.getDisplayXFactor())) {
				this.categoryList.get(i).setExpand(!this.categoryList.get(i).getExpand());
			}
			yShift+= yShiftHeight;
			if(this.categoryList.get(i).getExpand()) {
				while(j < this.categoryList.get(i).getCraftList().size()) {
					if(click(x+27, y+yShift, TTF2.craft.getWidth(this.categoryList.get(i).getCraftList().get(j).getItem().getStuffName()), TTF2.craft.getLineHeight()+3)) {
						this.selectedItem = this.categoryList.get(i).getCraftList().get(j);
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
	
	private static void drawSelectedItem(CraftableItem item) {
		
	}
	
	private static boolean click(int x, int y, float x_size, float y_size) {
		if(Mideas.mouseX() >= x && Mideas.mouseX() <= x+x_size && Mideas.mouseY() >= y && Mideas.mouseY() <= y+y_size) {
			if(!Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0) {
					return true;
				}
			}
		}
		return false;
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
	
	private Color getFontColor(int itemLevel) {
		return null;
	}
	
	private void drawButton(Category category, int x, int y) {
		if(category.getExpand()) {
			Draw.drawQuad(Sprites.reduce_category_craft, x, y+1);
		}
		else {
			Draw.drawQuad(Sprites.expand_category_craft, x, y+1);
		}
	}
	
	public int getPlayerLevel() {
		return this.playerLevel;
	}
}
