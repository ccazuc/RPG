package com.mideas.rpg.v2.game.item.stuff;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.mideas.rpg.v2.utils.Texture;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.jdo.JDOStatement;

public class BagManager {
	
	private static ArrayList<Bag> containerList = new ArrayList<Bag>();
	private static HashMap<Integer, Texture> bagsSprites = new HashMap<Integer, Texture>();
	
	public static void loadBags() throws SQLException {
		JDOStatement statement = Mideas.getJDO().prepare("SELECT id, sprite_id, name, quality, size, sellprice FROM item_container");
		statement.execute();
		while(statement.fetch()) {
			int id = statement.getInt();
			String sprite_id = statement.getString();
			String name = statement.getString();
			int quality = statement.getInt();
			int size = statement.getInt();
			int sellPrice = statement.getInt();
			Bag newPiece = new Bag(id, sprite_id, name, quality, size, sellPrice);
			containerList.add(newPiece);
		}
	}
	
	public static void loadBagsSprites() {
		bagsSprites.put(100001, Sprites.back_bag);
		bagsSprites.put(100003, Sprites.bag20);
		bagsSprites.put(100004, Sprites.bag12);
	}
	
	public static HashMap<Integer, Texture> getBagsSprites() {
		return bagsSprites;
	}
	
	public static ArrayList<Bag> getContainerList() {
		return containerList;
	}
	
	public static Bag getContainer(int id) {
		int i = 0;
		while(i < containerList.size()) {
			if(containerList.get(i).getId() == id) {
				return containerList.get(i);
			}
			i++;
		}
		return null;
	}
	
	public static boolean exists(int id) {
		return getContainer(id) != null;
	}
	
	public static Bag getClone(int id) {
		Bag tempContainer = getContainer(id);
		if(tempContainer != null) {
			return new Bag(tempContainer);
		}
		return null;
	}
}
