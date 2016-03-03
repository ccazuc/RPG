package com.mideas.rpg.v2.game.item.potion;

import java.sql.SQLException;
import java.util.ArrayList;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.jdo.JDOStatement;

public class PotionManager {
	
	private static ArrayList<Potion> potionList = new ArrayList<Potion>();
	private static int numberPotionLoaded;
	
	public static void loadPotions() throws SQLException, CloneNotSupportedException {
		JDOStatement statement = Mideas.getJDO().prepare("SELECT id, sprite_id, name, heal, mana, sellprice FROM potion");
		statement.execute();
		while(statement.fetch()) {
			int id = statement.getInt();
			String sprite_id = statement.getString();
			String name = statement.getString();
			int heal = statement.getInt();
			int mana = statement.getInt();
			int sellPrice = statement.getInt();
			Potion newPotion = new Potion(id, sprite_id, name, heal, mana, sellPrice);
			potionList.add(newPotion);
			numberPotionLoaded++;
		}
	}
	
	public static Potion getPotion(int id) {
		int i = 0;
		while(i < potionList.size()) {
			if(potionList.get(i).getId() == id) {
				return potionList.get(i);
			}
			i++;
		}
		return null;
	}
	
	public static Potion getClone(int id) {
		Potion temp = getPotion(id);
		if(temp != null) {
			return new Potion(temp);
		}
		return null;
	}
	
	public static boolean exists(int id) {
		return getPotion(id) != null;
	}
	
	public static int getNumberPotionLoaded() {
		return numberPotionLoaded;
	}
}
