package com.mideas.rpg.v2.game.profession;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.hud.Cast;
import com.mideas.rpg.v2.hud.CastBar;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.Draw;
import com.mideas.rpg.v2.utils.ScrollBar;
import com.mideas.rpg.v2.utils.Texture;

public class Profession {

	private ArrayList<Category> categoryList = new ArrayList<Category>();
	private String name;
	private int id;
	private int playerLevel = 370;
	CraftableItem selectedItem;
	private ScrollBar scrollBar;
	private boolean init;
	private boolean change = true;
	private int numberLine;
	private float y_offset;
	private static final int MAX_HEIGHT = 140;
	private static final Color BG_COLOR = new Color(0, 0, 0, .5f); 

	private Button craftButton = new Button(0, 0, 185, 34, "Create", 10) {
		@Override
		public void eventButtonClick() throws SQLException {
			CastBar.addCast(new Cast(Profession.this.selectedItem.getCraftLength(), Profession.this.selectedItem.getItem().getStuffName()) {
				@Override
				public void endCastEvent() {
					
				}
			});
		}
		
		@Override
		public boolean activateCondition() {
			if(Profession.this.selectedItem != null) {
				return canCraft(Profession.this.selectedItem);
			}
			return false;
		}
	};
	
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
			this.scrollBar = new ScrollBar(x+358*Mideas.getDisplayXFactor(), y+97*Mideas.getDisplayXFactor(), 125*Mideas.getDisplayXFactor(), Sprites.character_frame.getImageWidth()*Mideas.getDisplayXFactor(), Sprites.character_frame.getImageHeight()*Mideas.getDisplayXFactor(), false);
			if(this.categoryList.size() > 0 && this.categoryList.get(0).getCraftList().size() > 0) {
				this.selectedItem = this.categoryList.get(0).getCraftList().get(0);
			}
			this.craftButton.setX(x+206*Mideas.getDisplayXFactor());
			this.craftButton.setY(y+440*Mideas.getDisplayXFactor());
			this.craftButton.setButtonWidth(87);
			this.craftButton.setButtonHeight(23);
			this.init = true;
		}
		if(Display.wasResized()) {
			this.scrollBar.update(x+358*Mideas.getDisplayXFactor(), y+97*Mideas.getDisplayXFactor(), 125*Mideas.getDisplayXFactor());
			this.craftButton.setX(x+206*Mideas.getDisplayXFactor());
			this.craftButton.setY(y+440*Mideas.getDisplayXFactor());
			this.craftButton.setButtonWidth(87);
			this.craftButton.setButtonHeight(23);
		}
		if(this.change) {
			int i = 0;
			this.numberLine = 0;
			while(i < this.categoryList.size()) {
				if(this.categoryList.get(i).getExpand()) {
					this.numberLine+= this.categoryList.get(i).getCraftList().size();
				}
				this.numberLine+= 1;
				i++;
			}
			this.change = false;
		}
		Draw.drawQuad(Sprites.craft_frame, x, y);
		if(this.selectedItem != null) {
			drawSelectedItem(x+28*Mideas.getDisplayXFactor(), y+253*Mideas.getDisplayXFactor());
		}
		this.scrollBar.draw();
		this.craftButton.draw();
		x+= 26*Mideas.getDisplayXFactor();
		y+= 99*Mideas.getDisplayXFactor();
		final int Y_TOP = y;
		int i = 0;
		int j = 0;
		int yShift = 0;
		float yShiftHeight = 17*Mideas.getDisplayXFactor();
		this.y_offset = yShiftHeight*(this.numberLine-8)*this.scrollBar.getScrollPercentage();
		y-= this.y_offset;
		while(i < this.categoryList.size()) {
			j = 0;
			if(y+yShift >= Y_TOP && yShift+yShiftHeight-this.y_offset <= MAX_HEIGHT*Mideas.getDisplayXFactor() && this.categoryList.get(i).getCraftList().size() > 0) {
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
					if(y+yShift >= Y_TOP && yShift+yShiftHeight-this.y_offset <= MAX_HEIGHT*Mideas.getDisplayXFactor()) {
						if(this.categoryList.get(i).getCraftList().get(j) == this.selectedItem) {
							Draw.drawQuad(getColor(this.categoryList.get(i).getCraftList().get(j).getLevel()), x-5, y+yShift);
						}
						if(this.categoryList.get(i).getCraftList().get(j).getMouseDown()) {
							TTF2.craft.drawString(x+29, y+2+yShift, this.categoryList.get(i).getCraftList().get(j).getItem().getStuffName(), getFontColor(this.categoryList.get(i).getCraftList().get(j)));
						}
						else {
							TTF2.craft.drawString(x+27, y+yShift, this.categoryList.get(i).getCraftList().get(j).getItem().getStuffName(), getFontColor(this.categoryList.get(i).getCraftList().get(j)));
						}
					}
					yShift+= yShiftHeight;
					j++;
					if(yShift+yShiftHeight-this.y_offset >= MAX_HEIGHT*Mideas.getDisplayXFactor()) {
						break;
					}
				}
			}
			if(yShift+yShiftHeight-this.y_offset >= MAX_HEIGHT*Mideas.getDisplayXFactor()) {
				break;
			}
			i++;
		}
	}
	
	public void event(int x, int y) throws NoSuchAlgorithmException, SQLException {
		if(!this.init) {
			this.scrollBar = new ScrollBar(x+358*Mideas.getDisplayXFactor(), y+97*Mideas.getDisplayXFactor(), 125*Mideas.getDisplayXFactor(), Sprites.character_frame.getImageWidth()*Mideas.getDisplayXFactor(), Sprites.character_frame.getImageHeight()*Mideas.getDisplayXFactor(), false);
			if(this.categoryList.size() > 0 && this.categoryList.get(0).getCraftList().size() > 0) {
				this.selectedItem = this.categoryList.get(0).getCraftList().get(0);
			}
			this.craftButton.setX(x+206*Mideas.getDisplayXFactor());
			this.craftButton.setY(y+440*Mideas.getDisplayXFactor());
			this.craftButton.setButtonWidth(87);
			this.craftButton.setButtonHeight(23);
			this.init = true;
		}
		this.scrollBar.event();
		this.craftButton.event();
		x+= 26*Mideas.getDisplayXFactor();
		y+= 99*Mideas.getDisplayXFactor();
		final int Y_TOP = y;
		int i = 0;
		int j = 0;
		int yShift = 0;
		float yShiftHeight = 17*Mideas.getDisplayXFactor();
		y-= this.y_offset;
		float borderHeight = Sprites.craft_orange_selection.getImageHeight()*Mideas.getDisplayXFactor()-2;
		while(i < this.categoryList.size()) {
			j = 0;
			if(y+yShift >= Y_TOP && yShift+yShiftHeight-this.y_offset <= MAX_HEIGHT*Mideas.getDisplayXFactor() && this.categoryList.get(i).getCraftList().size() > 0) {
				checkMouseEventCategory(x, y+yShift, Sprites.craft_orange_selection.getImageWidth()*Mideas.getDisplayXFactor(), borderHeight, this.categoryList.get(i));
			}
			yShift+= yShiftHeight;
			if(this.categoryList.get(i).getExpand()) {
				while(j < this.categoryList.get(i).getCraftList().size()) {
					if(y+yShift >= Y_TOP && yShift+yShiftHeight-this.y_offset <= MAX_HEIGHT*Mideas.getDisplayXFactor()) {
						checkMouseEventItem(x, y+1+yShift, Sprites.craft_orange_selection.getImageWidth()*Mideas.getDisplayXFactor(), borderHeight, this.categoryList.get(i).getCraftList().get(j));
					}
					j++;
					yShift+= yShiftHeight;
					if(yShift+yShiftHeight-this.y_offset >= MAX_HEIGHT*Mideas.getDisplayXFactor()) {
						break;
					}
				}
			}
			if(yShift+yShiftHeight-this.y_offset >= MAX_HEIGHT*Mideas.getDisplayXFactor()) {
				break;
			}
			i++;
		}
	}
	
	private void drawSelectedItem(float x, float y) {
		Draw.drawQuad(IconsManager.getSprite37(this.selectedItem.getItem().getSpriteId()), x, y, 41*Mideas.getDisplayXFactor(), 39*Mideas.getDisplayXFactor());
		Draw.drawQuad(Sprites.profession_border, x, y);
		TTF2.craft.drawString(x+53*Mideas.getDisplayXFactor(), y+2, this.selectedItem.getItem().getStuffName(), Color.decode("#DDB500"));
		drawComponent(x-4, y+61*Mideas.getDisplayXFactor(), 0);
	}
	
	private void drawComponent(float x, float y, int number) {
		if(number < this.selectedItem.getNeededItemList().size()) {
			Draw.drawQuad(IconsManager.getSprite37(this.selectedItem.getNeededItem(number).getSpriteId()), x, y, 41*Mideas.getDisplayXFactor(), 39*Mideas.getDisplayXFactor());
			Draw.drawQuad(Sprites.profession_border, x, y);
			if(Mideas.bag().getItemNumber(this.selectedItem.getNeededItem(number)) < this.selectedItem.getNeededItemNumber(number)) {
				Draw.drawColorQuad(x, y, 42*Mideas.getDisplayXFactor(), 40*Mideas.getDisplayXFactor(), BG_COLOR);
			}
			TTF2.craft.drawStringShadow(x+39-TTF2.craft.getWidth(Integer.toString(Mideas.bag().getItemNumber(this.selectedItem.getNeededItem(number)))+"/"+this.selectedItem.getNeededItemNumber(number)), y+23, Integer.toString(Mideas.bag().getItemNumber(this.selectedItem.getNeededItem(number)))+"/"+this.selectedItem.getNeededItemNumber(number), Color.white, Color.black, 1, 1);
			TTF2.craft.drawStringShadow(x+45*Mideas.getDisplayXFactor(), y, this.selectedItem.getNeededItem(0).getStuffName(), Color.decode("#DDB500"), Color.black, 1, 1);
		}
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
					this.change = true;
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
	
	boolean canCraft(CraftableItem item) {
		int i = 0;
		while(i < item.getNeededItemList().size()) {
			if(Mideas.bag().getItemNumber(item.getNeededItem(i)) < item.getNeededItemNumber(i)) {
				return false;
			}
			i++;
		}
		return true;
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
	
	private void drawButton(Category category, float x, float y) {
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
