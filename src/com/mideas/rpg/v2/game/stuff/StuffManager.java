package com.mideas.rpg.v2.game.stuff;

import java.sql.SQLException;
import java.util.ArrayList;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.jdo.JDOStatement;

public class StuffManager {

	private static ArrayList<Stuff> stuffList = new ArrayList<Stuff>();
	
	public static void loadStuffs() throws SQLException, CloneNotSupportedException {
		JDOStatement statement = Mideas.getJDO().prepare("SELECT * FROM stuff");
		statement.execute();
		while(statement.fetch()) {
			int id = statement.getInt();
			String name = statement.getString();
			int armor = statement.getInt();
			int stamina = statement.getInt();
			int mana = statement.getInt();
			int critical = statement.getInt();
			int strength = statement.getInt();
			int sellPrice = statement.getInt();
			Stuff newPiece = new Stuff(StuffType.TRINKET, id, name, critical, strength, stamina, armor, mana, "", "", "", "", 0, sellPrice, 0, "");
			stuffList.add(newPiece);
		}
	}
	
	public static Stuff getStuff(int id) {
		int i = 0;
		while(i < stuffList.size()) {
			if(stuffList.get(i).getId() == id) {
				return stuffList.get(i);
			}
			i++;
		}
		return null;
	}
	
	public static Stuff getClone(int id) {
		Stuff temp = getStuff(id);
		if(temp != null) {
			return new Stuff(temp);
		}
		return null;
	}
	
}
