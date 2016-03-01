package com.mideas.rpg.v2.game.stuff.item.craft;

import com.mideas.rpg.v2.game.ClassType;
import com.mideas.rpg.v2.game.stuff.StuffType;
import com.mideas.rpg.v2.game.stuff.item.Item;

public class Craft extends Item{

	public Craft(StuffType type, ClassType[] classType, String sprite_id, int id, String name, int critical, int strength, int stamina, int armor, int mana, int price, int sellPrice,  int expCraftGiven) {
		super(type, classType, sprite_id, id, name, critical, strength, stamina, armor, mana, price, sellPrice, expCraftGiven);
	}
}
