package com.mideas.rpg.v2.game.stuff.mainHand;

import com.mideas.rpg.v2.game.stuff.Stuff;
import com.mideas.rpg.v2.game.stuff.StuffType;

public class MainHand extends Stuff {
	
	public MainHand(StuffType type, int id, String name, int critical, int strength, int stamina, int armor, int mana, String classe, String classe2, String classe3, String classe4, int price, int sellPrice,  int expCraftGiven, String slot) {
		super(type, id, name, critical, strength, stamina, armor, mana, classe, classe2, classe3, classe4, price, sellPrice, expCraftGiven, slot);
	}
	public MainHand(StuffType type, int id, String name, int critical, int strength, int stamina, int armor, int mana, String classe, String classe2, String classe3, int price, int sellPrice,  int expCraftGiven, String slot) {
		super(type, id, name, critical, strength, stamina, armor, mana, classe, classe2, classe3, price, sellPrice, expCraftGiven, slot);
	}
	public MainHand(StuffType type, int id, String name, int critical, int strength, int stamina, int armor, int mana, String classe, String classe2, int price, int sellPrice,  int expCraftGiven, String slot) {
		super(type, id, name, critical, strength, stamina, armor, mana, classe, classe2, price, sellPrice, expCraftGiven, slot);
	}
	public MainHand(StuffType type, int id, String name, int critical, int strength, int stamina, int armor, int mana, String classe, int price, int sellPrice,  int expCraftGiven, String slot) {
		super(type, id, name, critical, strength, stamina, armor, mana, classe, price, sellPrice, expCraftGiven, slot);
	}
}