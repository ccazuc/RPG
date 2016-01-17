package com.mideas.rpg.v2.game.stuff.chest;

import com.mideas.rpg.v2.game.stuff.Stuff;

public class Chest extends Stuff {
	
	public Chest(int id, String name, int critical, int strength, int stamina, int armor, int mana, String classe, String classe2, String classe3, String classe4, int price, int sellPrice, String slot) {
		super(id, name, critical, strength, stamina, armor, mana, classe, classe2, classe3, classe4, price, sellPrice, slot);
	}
	public Chest(int id, String name, int critical, int strength, int stamina, int armor, int mana, String classe, String classe2, String classe3, int price, int sellPrice, String slot) {
		super(id, name, critical, strength, stamina, armor, mana, classe, classe2, classe3, price, sellPrice, slot);
	}
	public Chest(int id, String name, int critical, int strength, int stamina, int armor, int mana, String classe, String classe2, int price, int sellPrice, String slot) {
		super(id, name, critical, strength, stamina, armor, mana, classe, classe2, price, sellPrice, slot);
	}
	public Chest(int id, String name, int critical, int strength, int stamina, int armor, int mana, String classe, int price, int sellPrice, String slot) {
		super(id, name, critical, strength, stamina, armor, mana, classe, price, sellPrice, slot);
	}
}
