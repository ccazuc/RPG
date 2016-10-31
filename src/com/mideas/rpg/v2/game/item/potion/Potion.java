package com.mideas.rpg.v2.game.item.potion;

import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;

public class Potion extends Item {

	private int doHeal;
	private String doHealString;
	private int doMana;
	private String doManaString;
	private int level;
	private String levelString;
	
	private final static String restore = "Restores ";
	private final static String required = " required";
	private final static String levelsString = "Level ";
	private final static String hpString = " Hp";
	private final static String manaString = " mana";
	
	private final static String empty = "";
	
	public Potion(int id) {
		super(id, empty, ItemType.POTION, empty, 1, 0, 1, 1);
	}

	public Potion(Potion potion) {
		super(potion.id, potion.sprite_id, ItemType.POTION, potion.name, 1, potion.sellPrice, potion.maxStack, potion.amount);
		this.doHealString = potion.doHealString;
		this.doManaString = potion.doManaString;
		this.levelString = potion.levelString;
		this.doHeal = potion.doHeal;
		this.doMana = potion.doMana;
		this.isLoaded = true;
	}
	
	public Potion(int id, String sprite_id, String name, int level, int doHeal, int doMana, int sellPrice, int amount) {
		super(id, sprite_id, ItemType.POTION, name, 1, sellPrice, 200, amount);
		this.doHeal = doHeal;
		this.doMana = doMana;
		this.level = level;
		buildAllString();
		this.isLoaded = true;
	}
	
	public void buildAllString() {
		buildDoHealString();
		buildDoManaString();
		buildLevelString();
	}
	
	public void buildLevelString() {
		this.levelString = levelsString+this.level+required;
	}
	
	public void buildDoHealString() {
		this.doHealString = restore+this.doHeal+hpString;
	}
	
	public void buildDoManaString() {
		this.doManaString = restore+this.doMana+manaString;
	}
	
	public String getLevelString() {
		return this.levelString;
	}
	
	public String getDoHealString() {
		return this.doHealString;
	}
	
	public String getDoManaString() {
		return this.doManaString;
	}
	
	public int getPotionHeal() {
		return this.doHeal;
	}
	
	public int getPotionMana() {
		return this.doMana;
	}
	
	public int getLevel() {
		return this.level;
	}
}
