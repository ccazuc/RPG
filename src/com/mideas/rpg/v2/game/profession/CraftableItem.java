package com.mideas.rpg.v2.game.profession;

import com.mideas.rpg.v2.game.item.Item;

public class CraftableItem {

	private int id;
	private int level;
	private Item item;
	private Item ressource1;
	private int ressource1Amount;
	private Item ressource2;
	private int ressource2Amount;
	private Item ressource3;
	private int ressource3Amount;
	private Item ressource4;
	private int ressource4Amount;
	private Item ressource5;
	private int ressource5Amount;
	private Item ressource6;
	private int ressource6Amount;
	private boolean mouseHover;
	private boolean mouseDown;
	
	public CraftableItem(int id, int level, Item item, Item ressource1, int ressource1Amount, Item ressource2, int ressource2Amount, Item ressource3, int ressource3Amount, Item ressource4, int ressource4Amount, Item ressource5, int ressource5Amount, Item ressource6, int ressource6Amount) {
		this.id = id;
		this.level = level;
		this.item = item;
		this.ressource1 = ressource1;
		this.ressource1Amount = ressource1Amount;
		this.ressource2 = ressource2;
		this.ressource2Amount = ressource2Amount;
		this.ressource3 = ressource3;
		this.ressource3Amount = ressource3Amount;
		this.ressource4 = ressource4;
		this.ressource4Amount = ressource4Amount;
		this.ressource5 = ressource5;
		this.ressource5Amount = ressource5Amount;
		this.ressource6 = ressource6;
		this.ressource6Amount = ressource6Amount;
	}
	
	public CraftableItem(CraftableItem craftableItem) {
		this.id = craftableItem.id;
		this.level = craftableItem.level;
		this.item = craftableItem.item;
		this.ressource1 = craftableItem.ressource1;
		this.ressource1Amount = craftableItem.ressource1Amount;
		this.ressource2 = craftableItem.ressource2;
		this.ressource2Amount = craftableItem.ressource2Amount;
		this.ressource3 = craftableItem.ressource3;
		this.ressource3Amount = craftableItem.ressource3Amount;
		this.ressource4 = craftableItem.ressource4;
		this.ressource4Amount = craftableItem.ressource4Amount;
		this.ressource5 = craftableItem.ressource5;
		this.ressource5Amount = craftableItem.ressource5Amount;
		this.ressource6 = craftableItem.ressource6;
		this.ressource6Amount = craftableItem.ressource6Amount;
	}
	
	public int getId() {
		return this.id;
	}
	
	public boolean getMouseHover() {
		return this.mouseHover;
	}
	
	public void setMouseHover(boolean hover) {
		this.mouseHover = hover;
	}
	
	public boolean getMouseDown() {
		return this.mouseDown;
	}
	
	public void setMouseDown(boolean down) {
		this.mouseDown = down;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public Item getItem() {
		return this.item;
	}
	
	public Item getRessource1() {
		return this.ressource1;
	}
	
	public int getRessource1Amount() {
		return this.ressource1Amount;
	}
	
	public Item getRessource2() {
		return this.ressource2;
	}
	
	public int getRessource2Amount() {
		return this.ressource2Amount;
	}
	
	public Item getRessource3() {
		return this.ressource3;
	}
	
	public int getRessource3Amount() {
		return this.ressource3Amount;
	}
	
	public Item getRessource4() {
		return this.ressource4;
	}
	
	public int getRessource4Amount() {
		return this.ressource4Amount;
	}
	
	public Item getRessource5() {
		return this.ressource5;
	}
	
	public int getRessource5Amount() {
		return this.ressource5Amount;
	}
	
	public Item getRessource6() {
		return this.ressource6;
	}
	
	public int getRessource6Amount() {
		return this.ressource6Amount;
	}
}
