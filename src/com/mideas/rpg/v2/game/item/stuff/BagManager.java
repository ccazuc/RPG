package com.mideas.rpg.v2.game.item.stuff;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.opengl.Texture;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.jdo.JDOStatement;

public class BagManager {
	
	private static ArrayList<Bag> containerList = new ArrayList<Bag>();
	private static HashMap<Integer, Texture> bagsSprites = new HashMap<Integer, Texture>();
	private static HashMap<Integer, Texture> bagsIconsSprites = new HashMap<Integer, Texture>();
	
	public static void loadBags() throws SQLException, CloneNotSupportedException {
		JDOStatement statement = Mideas.getJDO().prepare("SELECT id, sprite_id, name, size FROM container");
		statement.execute();
		while(statement.fetch()) {
			int id = statement.getInt();
			String sprite_id = statement.getString();
			String name = statement.getString();
			int size = statement.getInt();
			Bag newPiece = new Bag(id, sprite_id, name, size);
			containerList.add(newPiece);
		}
	}
	
	public static void loadBagsSprites() {
		bagsSprites.put(1, Sprites.back_bag);
		bagsSprites.put(3, Sprites.bag20);
		bagsSprites.put(4, Sprites.bag12);
	}
	
	public static HashMap<Integer, Texture> getBagsSprites() {
		return bagsSprites;
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
