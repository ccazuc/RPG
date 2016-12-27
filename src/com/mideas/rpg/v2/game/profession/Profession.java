package com.mideas.rpg.v2.game.profession;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.hud.Cast;
import com.mideas.rpg.v2.hud.CastBar;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.Color;
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
	private static final Color YELLOW = Color.decode("#DDB500");
	private static final Color ORANGE = Color.decode("#A8542A");
	private static final Color YELLOW_CRAFT = Color.decode("#B9B700");
	private static final Color GREEN = Color.decode("#2D852D");
	private static final Color GREY = Color.decode("#585758");
	static HashMap<Integer, Integer> possibleCraftList = new HashMap<Integer, Integer>();

	private Button craftButton = new Button(0, 0, 185, 34, "Create", 14, 1) {
		@Override
		public void eventButtonClick() {
			CastBar.addCast(new Cast(Profession.this.selectedItem.getCraftLength(), Profession.this.selectedItem.getItem().getStuffName()) {
				@Override
				public void endCastEvent() {
					int i = 0;
					while(i < Profession.this.selectedItem.getNeededItemList().size()) {
						if(Mideas.joueur1().bag().getNumberItemInBags(Profession.this.selectedItem.getNeededItem(i).getId()) >= Profession.this.selectedItem.getNeededItemNumber(i)) {
							Mideas.joueur1().deleteItem(Profession.this.selectedItem.getNeededItem(i), Profession.this.selectedItem.getNeededItemNumber(i));
						}
						else {
							return;
						}
						i++;
					}
					Mideas.joueur1().addItem(Profession.this.selectedItem.getItem(), 1);
				}
			});
		}
		
		@Override
		public boolean activateCondition() {
			if(Profession.this.selectedItem != null) {
				return Profession.possibleCraftList.get(Profession.this.selectedItem.getId()) > 0;
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
			if(this.scrollBar == null ) {
				this.scrollBar = new ScrollBar(x+358*Mideas.getDisplayXFactor(), y+97*Mideas.getDisplayYFactor(), 125*Mideas.getDisplayXFactor(), Sprites.character_frame.getImageWidth()*Mideas.getDisplayYFactor(), Sprites.character_frame.getImageHeight()*Mideas.getDisplayXFactor(), false, 17*Mideas.getDisplayXFactor());
				//System.out.println("SB init");
			}
			if(this.categoryList.size() > 0 && this.categoryList.get(0).getCraftList().size() > 0) {
				this.selectedItem = this.categoryList.get(0).getCraftList().get(0);
			}
			//updateSize(x, y);
			this.init = true;
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
		this.craftButton.draw();
		x+= 26*Mideas.getDisplayXFactor();
		y+= 99*Mideas.getDisplayYFactor();
		final int Y_TOP = y;
		int i = 0;
		int j = 0;
		int yShift = 0;
		int yShiftHeight = (int)(17*Mideas.getDisplayYFactor());
		if(this.numberLine > 8) {
			this.scrollBar.draw();
			this.y_offset = yShiftHeight*(this.numberLine-8)*this.scrollBar.getScrollPercentage();
			//this.y_offset = (int)((this.numberLine-8)*this.scrollBar.getScrollPercentage());
			//System.out.println((int)this.y_offset);
		}
		else {
			this.y_offset = 0;
		}
		y-= this.y_offset;
		/*i = calculateCategoryOffset();
		j = calculateCraftOffset(i);
		boolean init = false;
		//calculateCraftOffset(calculateCategoryOffset());
		//System.out.println("i offset: "+(int)(this.y_offset/yShiftHeight)+" c size: "+this.categoryList.size()+" "+(y+yShift >= Y_TOP)+" "+(yShift+yShiftHeight <= MAX_HEIGHT*Mideas.getDisplayYFactor())+" "+this.categoryList.get(0).getCraftList().size());
		while(i < this.categoryList.size()) {
			if(init) {
				j = 0;
			}
			if(j == 0 && y+yShift >= Y_TOP && yShift+yShiftHeight-this.y_offset <= MAX_HEIGHT*Mideas.getDisplayYFactor() && this.categoryList.get(i).getCraftList().size() > 0) {
				drawButton(this.categoryList.get(i), x, y+yShift);
				if(this.categoryList.get(i).getMouseDown()) {
					TTF2.get("FRIZQT", 13).drawString(x+22, y+2+yShift, this.categoryList.get(i).getName(), getColorsCategory(this.categoryList.get(i)));
				}
				else {
					TTF2.get("FRIZQT", 13).drawString(x+20, y+yShift, this.categoryList.get(i).getName(), getColorsCategory(this.categoryList.get(i)));
				}
			}
			yShift+= yShiftHeight;
			if(this.categoryList.get(i).getExpand() && this.categoryList.get(i).getCraftList().size() >= 1) {
				while(j < this.categoryList.get(i).getCraftList().size()) {
					if(y+yShift >= Y_TOP && yShift+yShiftHeight-this.y_offset <= MAX_HEIGHT*Mideas.getDisplayXFactor()) {
						if(this.categoryList.get(i).getCraftList().get(j) == this.selectedItem) {
							Draw.drawQuad(getColors(this.categoryList.get(i).getCraftList().get(j).getLevel()), x-5, y+yShift);
						}
						if(this.categoryList.get(i).getCraftList().get(j).getMouseDown()) {
							TTF2.get("FRIZQT", 13).drawString(x+29, y+2+yShift, this.categoryList.get(i).getCraftList().get(j).getItem().getStuffName(), getFontColors(this.categoryList.get(i).getCraftList().get(j)));
							TTF2.get("FRIZQT", 13).drawString(x+35+TTF2.get("FRIZQT", 13).getWidth(this.categoryList.get(i).getCraftList().get(j).getItem().getStuffName()), y+2+yShift, "["+possibleCraftList.get(this.categoryList.get(i).getCraftList().get(j).getId())+"]", getFontColors(this.categoryList.get(i).getCraftList().get(j)));
						}
						else {
							TTF2.get("FRIZQT", 13).drawString(x+27, y+yShift, this.categoryList.get(i).getCraftList().get(j).getItem().getStuffName(), getFontColors(this.categoryList.get(i).getCraftList().get(j)));
							TTF2.get("FRIZQT", 13).drawString(x+33+TTF2.get("FRIZQT", 13).getWidth(this.categoryList.get(i).getCraftList().get(j).getItem().getStuffName()), y+yShift, "["+possibleCraftList.get(this.categoryList.get(i).getCraftList().get(j).getId())+"]", getFontColors(this.categoryList.get(i).getCraftList().get(j)));
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
			init = true;
			i++;
		}*/
		while(i < this.categoryList.size()) {
			j = 0;
			if(y+yShift >= Y_TOP && yShift+yShiftHeight-this.y_offset <= MAX_HEIGHT*Mideas.getDisplayYFactor() && this.categoryList.get(i).getCraftList().size() > 0) {
				drawButton(this.categoryList.get(i), x, y+yShift);
				if(this.categoryList.get(i).getMouseDown()) {
					FontManager.get("FRIZQT", 13).drawString(x+22, y+2+yShift, this.categoryList.get(i).getName(), getColorCategory(this.categoryList.get(i)));
				}
				else {
					FontManager.get("FRIZQT", 13).drawString(x+20, y+yShift, this.categoryList.get(i).getName(), getColorCategory(this.categoryList.get(i)));
				}
			}
			yShift+= yShiftHeight;
			if(this.categoryList.get(i).getExpand() && this.categoryList.get(i).getCraftList().size() >= 1) {
				while(j < this.categoryList.get(i).getCraftList().size()) {
					if(y+yShift >= Y_TOP && yShift+yShiftHeight-this.y_offset <= MAX_HEIGHT*Mideas.getDisplayXFactor()) {
						if(this.categoryList.get(i).getCraftList().get(j) == this.selectedItem) {
							Draw.drawQuad(getColors(this.categoryList.get(i).getCraftList().get(j).getLevel()), x-5, y+yShift);
						}
						if(this.categoryList.get(i).getCraftList().get(j).getMouseDown()) {
							FontManager.get("FRIZQT", 13).drawString(x+29, y+2+yShift, this.categoryList.get(i).getCraftList().get(j).getItem().getStuffName(), getFontColors(this.categoryList.get(i).getCraftList().get(j)));
							FontManager.get("FRIZQT", 13).drawString(x+35+FontManager.get("FRIZQT", 13).getWidth(this.categoryList.get(i).getCraftList().get(j).getItem().getStuffName()), y+2+yShift, "["+possibleCraftList.get(this.categoryList.get(i).getCraftList().get(j).getId())+"]", getFontColors(this.categoryList.get(i).getCraftList().get(j)));
						}
						else {
							FontManager.get("FRIZQT", 13).drawString(x+27, y+yShift, this.categoryList.get(i).getCraftList().get(j).getItem().getStuffName(), getFontColors(this.categoryList.get(i).getCraftList().get(j)));
							FontManager.get("FRIZQT", 13).drawString(x+33+FontManager.get("FRIZQT", 13).getWidth(this.categoryList.get(i).getCraftList().get(j).getItem().getStuffName()), y+yShift, "["+possibleCraftList.get(this.categoryList.get(i).getCraftList().get(j).getId())+"]", getFontColors(this.categoryList.get(i).getCraftList().get(j)));
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
	
	/*private int calculateCategoryOffset() {
		int i = 0;
		int value = 0;
		while(i < this.categoryList.size() && value < this.y_offset) {
			if(this.categoryList.get(i).getExpand()) {
				value+= this.categoryList.get(i).getCraftList().size();
			}
			value++;
			if(value >= this.y_offset) {
				return i;
			}
			i++;
		}
		return i;
	}
	
	private int calculateCraftOffset(int i) {
		int j = 0;
		int value = 1;
		while(j < this.categoryList.get(i).getCraftList().size()) {
			value++;
			if(value >= this.y_offset) {
				return j;
			}
			j++;
		}
		return j;
	}*/
	
	public void event(int x, int y) {
		if(!this.init) {
			if(this.scrollBar == null ) {
				this.scrollBar = new ScrollBar(x+130*Mideas.getDisplayXFactor(), y-238*Mideas.getDisplayXFactor(), 125*Mideas.getDisplayXFactor(), Sprites.character_frame.getImageWidth()*Mideas.getDisplayXFactor(), Sprites.character_frame.getImageHeight()*Mideas.getDisplayXFactor(), false, 17*Mideas.getDisplayXFactor());
			}
			if(this.categoryList.size() > 0 && this.categoryList.get(0).getCraftList().size() > 0) {
				this.selectedItem = this.categoryList.get(0).getCraftList().get(0);
			}
			this.init = true;
		}
		if(this.numberLine > 8) {
			this.scrollBar.event();
		}
		this.craftButton.event();
		x+= 26*Mideas.getDisplayXFactor();
		y+= 99*Mideas.getDisplayYFactor();
		final int Y_TOP = y;
		int i = 0;
		int j = 0;
		int yShift = 0;
		float yShiftHeight = 17*Mideas.getDisplayYFactor();
		y-= this.y_offset;
		float borderHeight = Sprites.craft_orange_selection.getImageHeight()*Mideas.getDisplayYFactor()-2;
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
			if(yShift+yShiftHeight-this.y_offset >= MAX_HEIGHT*Mideas.getDisplayYFactor()) {
				break;
			}
			i++;
		}
	}
	
	private void drawSelectedItem(float x, float y) {
		Draw.drawQuad(IconsManager.getSprite37(this.selectedItem.getItem().getSpriteId()), x, y, 41*Mideas.getDisplayXFactor(), 39*Mideas.getDisplayXFactor());
		Draw.drawQuad(Sprites.profession_border, x, y);
		FontManager.get("FRIZQT", 13).drawString(x+53*Mideas.getDisplayXFactor(), y+2, this.selectedItem.getItem().getStuffName(), YELLOW);
		drawComponent(x-4, y+61*Mideas.getDisplayXFactor(), 0);
	}
	
	private void drawComponent(float x, float y, int number) {
		if(number < this.selectedItem.getNeededItemList().size()) {
			Draw.drawQuad(IconsManager.getSprite37(this.selectedItem.getNeededItem(number).getSpriteId()), x, y, 41*Mideas.getDisplayXFactor(), 39*Mideas.getDisplayXFactor());
			Draw.drawQuad(Sprites.profession_border, x, y);
			if(Mideas.joueur1().bag().getItemNumber(this.selectedItem.getNeededItem(number)) < this.selectedItem.getNeededItemNumber(number)) {
				Draw.drawColorQuad(x, y, 42*Mideas.getDisplayXFactor(), 40*Mideas.getDisplayXFactor(), BG_COLOR);
			}
			FontManager.get("FRIZQT", 13).drawStringShadow(x+39-FontManager.get("FRIZQT", 13).getWidth(Integer.toString(Mideas.joueur1().bag().getItemNumber(this.selectedItem.getNeededItem(number)))+"/"+this.selectedItem.getNeededItemNumber(number)), y+23, Integer.toString(Mideas.joueur1().bag().getItemNumber(this.selectedItem.getNeededItem(number)))+"/"+this.selectedItem.getNeededItemNumber(number), Color.WHITE, Color.BLACK, 1, 1);
			FontManager.get("FRIZQT", 13).drawStringShadow(x+45*Mideas.getDisplayXFactor(), y, this.selectedItem.getNeededItem(0).getStuffName(), YELLOW, Color.BLACK, 1, 1);
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
	
	public void updateNumberPossibleCraft() {
		int i = 0;
		int j = 0;
		int k = 0;
		int amount = 0;
		boolean init = false;
		while(i < this.categoryList.size()) {
			j = 0;
			while(j < this.categoryList.get(i).getCraftList().size()) {
				k = 0;
				if(!possibleCraftList.containsKey(this.categoryList.get(i).getCraftList().get(j))) {
					while(k < this.categoryList.get(i).getCraftList().get(j).getNeededItemList().size()) {
						if(!init) {
							amount = Mideas.joueur1().bag().getNumberItemInBags(this.categoryList.get(i).getCraftList().get(j).getNeededItem(k).getId())/ this.categoryList.get(i).getCraftList().get(j).getNeededItemNumber(k);
						}
						else if(Mideas.joueur1().bag().getNumberItemInBags(this.categoryList.get(i).getCraftList().get(j).getNeededItem(k).getId())/ this.categoryList.get(i).getCraftList().get(j).getNeededItemNumber(k) < amount) {
							amount = Mideas.joueur1().bag().getNumberItemInBags(this.categoryList.get(i).getCraftList().get(j).getNeededItem(k).getId())/ this.categoryList.get(i).getCraftList().get(j).getNeededItemNumber(k);
						}
						k++;
					}
					possibleCraftList.put(this.categoryList.get(i).getCraftList().get(j).getId(), amount);
				}
				j++;
			}
			i++;
 		}
	}
	
	private static Color getColorCategory(Category category) {
		if(category.getMouseHover()) {
			return Color.WHITE;
		}
		return YELLOW;
		
	}
	
	public void updateSize(float x, float y) {
		//System.out.println("Size updated");
		if(this.scrollBar != null) {
			this.scrollBar.update(x+358*Mideas.getDisplayXFactor(), y+97*Mideas.getDisplayXFactor(), 125*Mideas.getDisplayYFactor(), 17*Mideas.getDisplayXFactor());
		}
		else {
			this.scrollBar = new ScrollBar(x+358*Mideas.getDisplayXFactor(), y+97*Mideas.getDisplayXFactor(), 125*Mideas.getDisplayYFactor(), Sprites.character_frame.getImageWidth()*Mideas.getDisplayXFactor(), Sprites.character_frame.getImageHeight()*Mideas.getDisplayYFactor(), false, 16*Mideas.getDisplayXFactor());
		}
		this.craftButton.setX(x+206*Mideas.getDisplayXFactor());
		this.craftButton.setY(y+440*Mideas.getDisplayXFactor());
		this.craftButton.setButtonWidth(87);
		this.craftButton.setButtonHeight(23);
	}
	
	private Texture getColors(int itemLevel) {
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
	
	private Color getFontColors(CraftableItem item) {
		int itemLevel = item.getLevel();
		if(item.getMouseHover() || this.selectedItem == item) {
			return Color.WHITE;
		}
		if(this.playerLevel-itemLevel < 15) {
			return ORANGE;
		}
		if(this.playerLevel-itemLevel < 30) {
			return YELLOW_CRAFT;
		}
		if(this.playerLevel-itemLevel < 45) {
			return GREEN;
		}
		if(this.playerLevel-itemLevel > 45) {
			return GREY;
		}
		return null;
	}
	
	private static void drawButton(Category category, float x, float y) {
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
