package com.mideas.rpg.v2.game.profession;

import java.util.ArrayList;


public class Category {

	private ArrayList<CraftableItem> craftList = new ArrayList<CraftableItem>();
	private int id;
	private String name;
	
	public Category(int id, String name, CraftableItem item1, CraftableItem item2, CraftableItem item3, CraftableItem item4, CraftableItem item5, CraftableItem item6, CraftableItem item7, CraftableItem item8, CraftableItem item9, CraftableItem item10) {
		this.name = name;
		this.id = id;
		this.craftList.add(item1);
		this.craftList.add(item2);
		this.craftList.add(item3);
		this.craftList.add(item4);
		this.craftList.add(item5);
		this.craftList.add(item6);
		this.craftList.add(item7);
		this.craftList.add(item8);
		this.craftList.add(item9);
		this.craftList.add(item10);
	}
	
	public ArrayList<CraftableItem> getCraftList() {
		return this.craftList;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getId() {
		return this.id;
	}
}
